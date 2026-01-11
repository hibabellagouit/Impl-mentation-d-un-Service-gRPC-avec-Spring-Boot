package io.grpc.controllers;

import io.grpc.services.CompteService;
import io.grpc.stubs.*;
import io.grpc.Status;
import io.grpc.stub.StreamObserver;
import net.devh.boot.grpc.server.service.GrpcService;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@GrpcService
public class CompteServiceImpl extends CompteServiceGrpc.CompteServiceImplBase {

    private final CompteService compteService;

    public CompteServiceImpl(CompteService compteService) {
        this.compteService = compteService;
    }

    private io.grpc.stubs.Compte convertCompteEntityToGrpc(io.grpc.entities.Compte entity) {
        return io.grpc.stubs.Compte.newBuilder()
                .setId(entity.getId())
                .setSolde(entity.getSolde())
                .setDateCreation(entity.getDateCreation())
                .setType(TypeCompte.valueOf(entity.getType()))
                .build();
    }

    @Override
    public void allComptes(GetAllComptesRequest request, StreamObserver<GetAllComptesResponse> responseObserver) {
        List<io.grpc.stubs.Compte> comptes = compteService.findAllComptes().stream()
                .map(this::convertCompteEntityToGrpc)
                .collect(Collectors.toList());

        Collections.reverse(comptes);

        responseObserver.onNext(GetAllComptesResponse.newBuilder()
                .addAllComptes(comptes)
                .build());
        responseObserver.onCompleted();
    }

    @Override
    public void compteById(GetCompteByIdRequest request, StreamObserver<GetCompteByIdResponse> responseObserver) {
        io.grpc.entities.Compte entity = compteService.findCompteById(request.getId());

        if (entity != null) {
            io.grpc.stubs.Compte grpcCompte = convertCompteEntityToGrpc(entity);
            responseObserver.onNext(GetCompteByIdResponse.newBuilder()
                    .setCompte(grpcCompte)
                    .build());
            responseObserver.onCompleted();
        } else {
            responseObserver.onError(Status.NOT_FOUND
                    .withDescription("Compte non trouvé avec l'ID: " + request.getId())
                    .asRuntimeException());
        }
    }

    @Override
    public void saveCompte(SaveCompteRequest request, StreamObserver<SaveCompteResponse> responseObserver) {
        var compteReq = request.getCompte();

        // Création de l'entité JPA
        var compteEntity = new io.grpc.entities.Compte();
        compteEntity.setSolde(compteReq.getSolde());
        compteEntity.setDateCreation(compteReq.getDateCreation());
        compteEntity.setType(compteReq.getType().name());

        // Sauvegarde dans la base de données
        var savedCompte = compteService.saveCompte(compteEntity);

        // Conversion en message gRPC
        var grpcCompte = convertCompteEntityToGrpc(savedCompte);

        responseObserver.onNext(SaveCompteResponse.newBuilder()
                .setCompte(grpcCompte)
                .build());
        responseObserver.onCompleted();
    }

        @Override
        public void totalSolde(GetTotalSoldeRequest request, StreamObserver<GetTotalSoldeResponse> responseObserver) {
        var comptes = compteService.findAllComptes();

        // Agrégation des statistiques de solde
        var summary = comptes.stream()
            .mapToDouble(io.grpc.entities.Compte::getSolde)
            .summaryStatistics();

        var stats = SoldeStats.newBuilder()
            .setCount((int) summary.getCount())
            .setSum((float) summary.getSum())
            .setAverage(summary.getCount() == 0 ? 0f : (float) summary.getAverage())
            .build();

        var response = GetTotalSoldeResponse.newBuilder()
            .setStats(stats)
            .build();

        responseObserver.onNext(response);
        responseObserver.onCompleted();
        }
}
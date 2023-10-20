package com.techscript.spot82.configuracao;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.DocumentReference;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.FirestoreOptions;
import com.google.cloud.firestore.WriteResult;
import com.google.firebase.cloud.FirestoreClient;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ExecutionException;

@Configuration
public class GravarDados {

    public void gravarDadosDoUsuario(Object object, String tabela) throws ExecutionException, InterruptedException {

        // Inicialize o Firestore
        FirestoreOptions firestoreOptions = FirestoreOptions.getDefaultInstance();
        Firestore firestore = FirestoreClient.getFirestore();

        // Referencie a coleção no Firestore onde deseja salvar os dados (por exemplo, "usuarios")
        DocumentReference documentReference = firestore.collection(tabela).document();

        // Salve os dados na referência
        ApiFuture<WriteResult> result = documentReference.set(object);

        // Aguarde a conclusão da operação (opcional)
        WriteResult writeResult = result.get();

    }
}

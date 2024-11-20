package com.aluracursos.screenmatch.service;

import com.theokanning.openai.completion.CompletionRequest;
import com.theokanning.openai.service.OpenAiService;

public class ConsultaCHATGPTAPI {

    public static String obtenerTraduccion(String texto) {
        OpenAiService service = new OpenAiService("");
        //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
        CompletionRequest requisicion = CompletionRequest.builder()
                .model("gpt-3.5-turbo-instruct")
                .prompt("traduce a español el siguiente texto: " + texto)
                .maxTokens(1000)
                .temperature(0.7)
                .build();

        var respuesta = service.createCompletion(requisicion);
        return respuesta.getChoices().get(0).getText();
    }
}

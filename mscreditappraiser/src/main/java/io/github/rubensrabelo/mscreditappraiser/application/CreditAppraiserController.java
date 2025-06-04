package io.github.rubensrabelo.mscreditappraiser.application;

import io.github.rubensrabelo.mscreditappraiser.application.exceptions.ClientDataNotFoundException;
import io.github.rubensrabelo.mscreditappraiser.application.exceptions.MicroservicesCommunicationErrorException;
import io.github.rubensrabelo.mscreditappraiser.domain.model.ClientStatus;
import io.github.rubensrabelo.mscreditappraiser.domain.model.EvaluationData;
import io.github.rubensrabelo.mscreditappraiser.domain.model.FeedbackCustomerEvaluation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credit-assessments")
public class CreditAppraiserController {

    private final CreditAppraiserService creditAppraiserService;

    public CreditAppraiserController(CreditAppraiserService creditAppraiserService) {
        this.creditAppraiserService = creditAppraiserService;
    }

    @GetMapping
    public String status() {
        return "OK";
    }

    @GetMapping(value = "client-status", params = "cpf")
    public ResponseEntity checkCustomerStatus(@RequestParam("cpf") String cpf) {
        try {
            ClientStatus clientStatus = creditAppraiserService.getCustomerStatus(cpf);
            return ResponseEntity.ok(clientStatus);
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroservicesCommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity performEvaluation(@RequestBody EvaluationData data) {
        try {
            FeedbackCustomerEvaluation feedbackCustomerEvaluation = creditAppraiserService
                    .performEvaluation(data.cpf(), data.income());
            return ResponseEntity.ok(feedbackCustomerEvaluation);
        } catch(ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (MicroservicesCommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}

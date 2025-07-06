package com.team_3.School_Medical_Management_System.Controller;

import com.team_3.School_Medical_Management_System.DTO.*;
import com.team_3.School_Medical_Management_System.InterFaceSerivce.Post_vaccination_observationsServiceInterFace;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/post_vaccination_observations")
public class Post_vaccination_observationController {
    private Post_vaccination_observationsServiceInterFace postVaccinationObservationsServiceInterFaceInterFace;
    @Autowired
    public Post_vaccination_observationController(Post_vaccination_observationsServiceInterFace postVaccinationObservationsServiceInterFaceInterFace) {
        this.postVaccinationObservationsServiceInterFaceInterFace = postVaccinationObservationsServiceInterFaceInterFace;
    }


    @GetMapping
    @Operation(summary = "Lấy hết danh sách quan sát")
    public List<Post_vaccination_observationsDTO> getPostVaccinationObservations() {
        return postVaccinationObservationsServiceInterFaceInterFace.getPost_vaccination_observations();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Post_vaccination_observationsDTO> getPostVaccinationObservations(@PathVariable int id) {
        var p = postVaccinationObservationsServiceInterFaceInterFace.getPost_vaccination_observation(id);
        if(p == null) {
            return ResponseEntity.notFound().build();
        }else {
            return ResponseEntity.ok(p);
        }
    }

    @PostMapping
    @Operation(summary = "Nhập")
    public ResponseEntity<Post_vaccination_observationsDTO> addPostVaccinationObservations(@RequestBody Post_vaccination_observationsDTO p) {
        var addPostVaccinationObservations = postVaccinationObservationsServiceInterFaceInterFace.addPost_vaccination_observation(p);
        if(addPostVaccinationObservations == null) {
            return ResponseEntity.badRequest().build();
        }else {
            return ResponseEntity.ok(addPostVaccinationObservations);
        }
    }

    @PutMapping("/edit_Post_vaccination_observation/{id}")
    @Operation(summary = "Cập nhập")
    public ResponseEntity<Post_vaccination_observations_edit_DTO> updateVaccine(
            @PathVariable("id") int id,
            @RequestBody Post_vaccination_observations_edit_DTO dto) {

       dto.setObservation_id(id);

        if (postVaccinationObservationsServiceInterFaceInterFace.getPost_vaccination_observation(id) == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        var updated = postVaccinationObservationsServiceInterFaceInterFace.updatePost_vaccination_observation(dto);
        return updated != null
                ? new ResponseEntity<>(updated, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
    }


    @PostMapping("/post_vaccination_observation-records/send-email")
    public ResponseEntity<Post_vaccination_observations_SendForParent_DTO> sendVaccinationEmail(
            @RequestBody Post_vaccination_observations_SendForParent_DTO records) {
        var result = postVaccinationObservationsServiceInterFaceInterFace.addPost_vaccination_observationByEmail(records);
        return ResponseEntity.ok(result);
    }


    @PutMapping("/post_vaccination_observation/resend/{Id}")
    public ResponseEntity<?> resend(@PathVariable Integer Id, @RequestBody Post_vaccination_observations_edit_Update_SendParent_DTO dto) {
        var result = postVaccinationObservationsServiceInterFaceInterFace.updateAndResendEmail(Id, dto);
        return ResponseEntity.ok(result);
    }
}

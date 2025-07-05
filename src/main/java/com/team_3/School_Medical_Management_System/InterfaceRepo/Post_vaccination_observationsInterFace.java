package com.team_3.School_Medical_Management_System.InterfaceRepo;

import com.team_3.School_Medical_Management_System.Model.Post_vaccination_observations;

import java.util.List;

public interface Post_vaccination_observationsInterFace {
    public List<Post_vaccination_observations> getPost_vaccination_observations();
    public Post_vaccination_observations getPost_vaccination_observation(Integer id);
    public Post_vaccination_observations addPost_vaccination_observation(Post_vaccination_observations post_vaccination_observation);
    public Post_vaccination_observations updatePost_vaccination_observation(Post_vaccination_observations post_vaccination_observation);

}

package diegosWafles.domain.port.input;

import diegosWafles.domain.model.dto.UserRegisterDTO;
import diegosWafles.domain.model.entities.User;

public interface AuthServicePort {
    String login(String email, String password);
    User register(UserRegisterDTO dto);
}

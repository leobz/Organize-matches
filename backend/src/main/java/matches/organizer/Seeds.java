package matches.organizer;

import matches.organizer.domain.User;
import matches.organizer.service.MatchService;
import matches.organizer.storage.UserRepository;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Seeds implements InitializingBean {

    @Autowired
    private MatchService matchService;
    @Autowired
    private UserRepository userRepository;


    @Override
    public void afterPropertiesSet() throws Exception {
        var user =  new User("User", "User","0303456", "pp@g.com", "Password");
        userRepository.add(user);
        matchService.createAndSaveRandomMatch();
    }
}
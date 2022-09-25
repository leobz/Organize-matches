package matches.organizer;

import matches.organizer.service.MatchService;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class Seeds implements InitializingBean {

    @Autowired
    private MatchService matchService;


    @Override
    public void afterPropertiesSet() throws Exception {
        matchService.createAndSaveRandomMatch();
    }
}
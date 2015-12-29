package solr.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import solr.cores.UserCoreServiceImpl;

/**
 * Created by zhou on 2015/12/28.
 */
@Service
public class SlorUserService {

    @Autowired
    private UserCoreServiceImpl userCoreService;


    /**
     *
     */
}

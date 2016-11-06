package controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import service.ScheduleService;

/**
 * Created by lu on 2016/10/15.
 */
@Controller
public class ScheduleController {

    static Logger logger = LogManager.getLogger(ScheduleController.class.getName());

    @Autowired
    private ScheduleService scheduleService;





}

package com.wenyizhou.job.controller;

import com.wenyizhou.job.model.Response;
import com.wenyizhou.job.model.User;
import com.wenyizhou.job.service.IJobService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
@RequestMapping("/job")
public class JobController {
    @Autowired
    private IJobService iJobService;
    @RequestMapping("/findJob")
    public ModelAndView findJob(@RequestParam String jobName,@RequestParam String jobPlace){
        //return new ModelAndView("index");
        System.out.println(jobName+"  "+jobPlace);
        return new ModelAndView("redirect:/index");
    }
    @GetMapping("/jobList")
    public Response jobList(Integer page){
        return iJobService.jobList(page);
    }
}

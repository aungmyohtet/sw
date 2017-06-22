package com.frobom.sw.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.frobom.sw.entity.Project;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.ProjectFormValidator;

@Controller
public class ProjectController {
    
    @Autowired
    @Qualifier("projectFormValidator")
    private ProjectFormValidator projectFormValidator;

    @Autowired
    private ProjectService projectService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    @RequestMapping(value = "/projects/add", method = RequestMethod.GET)
    public String addProjectForm(Model model) {
        model.addAttribute("project", new Project());
        model.addAttribute("projects", projectService.findAll());
        return "addProject";
    }

    @RequestMapping(value = "/projects/add", method = RequestMethod.POST)
    public String saveProject(@Validated @ModelAttribute Project project, BindingResult result, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            return "addProject";
        }
        projectFormValidator.validate(project, result);
        if (result.hasErrors()) {
            model.addAttribute("projects", projectService.findAll());
            return "addProject";
        }
        projectService.save(project);
        model.addAttribute("projects", this.projectService.findAll());
        return "redirect:/projects/add";
    }

    @RequestMapping(value = "/projects/{id}", method = RequestMethod.GET)
    public String showDetails(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        return "projectDetails";
    }
}

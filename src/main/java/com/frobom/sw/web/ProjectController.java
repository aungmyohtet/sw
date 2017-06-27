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

import com.frobom.sw.entity.MailAddress;
import com.frobom.sw.entity.Project;
import com.frobom.sw.service.MailAddressService;
import com.frobom.sw.service.ProjectService;
import com.frobom.sw.validator.ProjectFormValidator;

@Controller
public class ProjectController {

    @Autowired
    @Qualifier("projectFormValidator")
    private ProjectFormValidator projectFormValidator;

    @Autowired
    private ProjectService projectService;

    @Autowired
    private MailAddressService mailAddressService;

    public void setProjectService(ProjectService projectService) {
        this.projectService = projectService;
    }

    public void setMailAddressService(MailAddressService mailAddressService) {
        this.mailAddressService = mailAddressService;
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

    @RequestMapping(value = "/projects/edit/{id}", method = RequestMethod.GET)
    public String showEditForm(@PathVariable("id") int id, Model model) {
        model.addAttribute("project", projectService.findById(id));
        return "edit_project";
    }

    @RequestMapping(value="/projects/edit/{id}", method=RequestMethod.POST)
    public String updateProject(@Validated @ModelAttribute Project project, BindingResult result, Model model) {
        if(result.hasErrors()){
            return "edit_project";
        }
        projectService.update(project);
        return "redirect:/projects/add";
    }

    @RequestMapping(value="/projects/delete/{id}", method=RequestMethod.GET)
    public String deleteProject(@PathVariable("id") int id, Model model) {
        projectService.delete(id);
        return "redirect:/projects/add";
    }

    @RequestMapping(value="/projects/{id}/add/mailAddress", method=RequestMethod.GET)
    public String showAddMailAddressForm(@PathVariable("id") int id, Model model){
        model.addAttribute("mailAddress", new MailAddress());
        model.addAttribute("mailAddresses", mailAddressService.findAll());
        model.addAttribute("project", projectService.findById(id));
        model.addAttribute("addresses", projectService.findMailAddressesByID(id));
        return "add_MailAddress_To_Project";
    }

    @RequestMapping(value = "/projects/{id}/add/mailAddress", method = RequestMethod.POST)
    public String addMailAddressToProject(@Validated @ModelAttribute Project project, BindingResult projResult, @Validated @ModelAttribute MailAddress mailAddress,
            BindingResult mailResult, Model model) {
        if (projResult.hasErrors() || mailResult.hasErrors()) {
            model.addAttribute("project", projectService.findById(project.getId()));
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            model.addAttribute("addresses", projectService.findMailAddressesByID(project.getId()));
            return "add_MailAddress_To_Project";
        }

        projectFormValidator.validate(project, projResult, mailAddress, mailResult);
        if (projResult.hasErrors() || mailResult.hasErrors()) {
            model.addAttribute("project", projectService.findById(project.getId()));
            model.addAttribute("mailAddresses", mailAddressService.findAll());
            model.addAttribute("addresses", projectService.findMailAddressesByID(project.getId()));
            return "add_MailAddress_To_Project";
        }

        projectService.addMailAddressToProject(mailAddress.getAddress(), project.getName());
        return "redirect:/projects/add";
    }
}

package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Owner;
import com.springsource.petclinic.domain.Pet;
import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import com.springsource.petclinic.reference.PetType;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.finder.RooWebFinder;
import org.springframework.validation.BindingResult;

@RequestMapping("/pets")
@Controller
@RooWebScaffold(path = "pets", formBackingObject = Pet.class)
@RooWebFinder
public class PetController {
	
    @RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Pet pet, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, pet);
            return "pets/create";
        }
        uiModel.asMap().clear();
        pet.persist();
        return "redirect:/pets?form";
        //return "redirect:/pets/" + encodeUrlPathSegment(pet.getId().toString(), httpServletRequest);
    }
    
    void populateEditForm(Model uiModel, Pet pet) {
    	List<Pet> pets = new ArrayList<Pet>();
    	
    	pets.addAll(Pet.findAllPets());
    	
        uiModel.addAttribute("pet", pet);
        uiModel.addAttribute("owners", Owner.findAllOwners());
        uiModel.addAttribute("pettypes", Arrays.asList(PetType.values()));
        uiModel.addAttribute("pets", pets);
    }

}

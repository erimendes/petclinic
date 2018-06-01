package com.springsource.petclinic.web;
import com.springsource.petclinic.domain.Owner;
import com.springsource.petclinic.domain.Pet;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.roo.addon.web.mvc.controller.scaffold.RooWebScaffold;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@RequestMapping("/owners")
@Controller
@RooWebScaffold(path = "owners", formBackingObject = Owner.class)
public class OwnerController {
	@RequestMapping(method = RequestMethod.POST, produces = "text/html")
    public String create(@Valid Owner owner, BindingResult bindingResult, Model uiModel, HttpServletRequest httpServletRequest) {
        if (bindingResult.hasErrors()) {
            populateEditForm(uiModel, owner);
            return "owners/create";
        }
        uiModel.asMap().clear();
        owner.persist();
        return "redirect:/owners?form";
        //return "redirect:/owners/" + encodeUrlPathSegment(owner.getId().toString(), httpServletRequest);
    }

	void populateEditForm(Model uiModel, Owner owner) {
    	List<Owner> owners = new ArrayList<Owner>();
    	
    	owners.addAll(Owner.findAllOwners());
		
        uiModel.addAttribute("owner", owner);
        addDateTimeFormatPatterns(uiModel);
        uiModel.addAttribute("pets", Pet.findAllPets());
        uiModel.addAttribute("owners", owners);
    }
}

package org.springframework.samples.petclinic.feeding;

import java.util.Collection;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.samples.petclinic.pet.Pet;
import org.springframework.samples.petclinic.pet.PetService;
import org.springframework.samples.petclinic.pet.PetValidator;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

public class FeedingController {
    private static final String VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM = "feedings/createOrUpdateFeedingForm";
    private static final String HOME = "welcome";

    private final FeedingService feedingService;

    private final PetService petService;

    @Autowired
    public FeedingController(FeedingService feedingService, PetService petService) {
        this.feedingService = feedingService;
        this.petService = petService;
    }

    @ModelAttribute("feedingTypes")
    public Collection<FeedingType> populateFeedingTypes() {
        return this.feedingService.findAllFeedingTypes();
    }

    @ModelAttribute("pets")
    public Collection<Pet> populatePets() {
        return this.petService.getAllPets();
    }

    @InitBinder("feeding")
    public void initFeedingBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new FeedingValidator());
    }

    @InitBinder("pet")
    public void initPetBinder(WebDataBinder dataBinder) {
        dataBinder.setValidator(new PetValidator());
    }

    @GetMapping(value = "/create")
    public String initCreationForm(ModelMap model) {
        Feeding feeding = new Feeding();
        model.put("feeding", feeding);
        return VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM;
    }

    @PostMapping(value = "/create")
    public String processCreationForm(@Valid Feeding feeding, BindingResult result, ModelMap model)
            throws UnfeasibleFeedingException {
        if (result.hasErrors()) {
            model.put("feeding", feeding);
            return VIEWS_FEEDINGS_CREATE_OR_UPDATE_FORM;
        } else {
            this.feedingService.save(feeding);
            return HOME;
        }
    }
}

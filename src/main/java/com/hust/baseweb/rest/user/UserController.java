package com.hust.baseweb.rest.user;

import com.hust.baseweb.entity.Party;
import com.hust.baseweb.entity.SecurityGroup;
import com.hust.baseweb.entity.SecurityPermission;
import com.hust.baseweb.entity.UserLogin;
import com.hust.baseweb.model.PersonModel;
import com.hust.baseweb.model.dto.DPersonDetailModel;
import com.hust.baseweb.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * UserController
 */
// @RepositoryRestController
// @ExposesResourceFor(DPerson.class)
@RestController
@AllArgsConstructor(onConstructor = @__(@Autowired))
@Log4j2
public class UserController {
    public static final String EDIT_REL = "edit";
    public static final String DELETE_REL = "delete";
    private UserService userService;

    @PostMapping(path = "/user")
    public ResponseEntity<?> save(@RequestBody PersonModel personModel, Principal principal) {
        // Resources<String> resources = new Resources<String>(producers);\\
        Party party;
        try {
            party = userService.save(personModel, principal.getName());
        } catch (Exception e) {
            e.printStackTrace();

            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
        // DPerson person=
        try {
            return ResponseEntity.created(new URI("str")).body(party);
        } catch (URISyntaxException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
        }
    }

    @GetMapping(path = "/users")
    public ResponseEntity<?> getUsers(Pageable page,
                                      @RequestParam(name = "search", required = false) String searchString,
                                      @RequestParam(name = "filter", required = false) String filterString) {
        log.info("::getUsers, searchString = " + searchString);

        return ResponseEntity.ok().body(userService.findPersonByFullName(page, searchString));
    }

    @GetMapping(path = "/users/{partyId}")
    public ResponseEntity<?> getUsersDetail(@PathVariable String partyId, Principal principal) {
        DPerson dPerson = userService.findByPartyId(partyId);
        DPersonDetailModel detailModel = new DPersonDetailModel(dPerson);
        UserLogin userLogin = userService.findById(principal.getName());

        List<SecurityPermission> permissionList = new ArrayList<>();
        for (SecurityGroup securityGroup : userLogin.getRoles()) {
            permissionList.addAll(securityGroup.getPermissions());
        }
        List<SecurityPermission> lf = permissionList.stream().filter(pe -> "USER_CREATE".equals(pe.getPermissionId())).collect(Collectors.toList());
        if (lf.size() > 0) {
            detailModel.add(new Link("/user", EDIT_REL));
            detailModel.add(new Link("/user", DELETE_REL));
        }
        return ResponseEntity.ok().body(detailModel);
    }

    /*
     * @GetMapping(path = "/users") public ResponseEntity<?> getUsers(Pageable page,
     *
     * @RequestParam(name = "filtering", required = false) String filterString) {
     * SortAndFiltersInput sortAndFiltersInput = null; if (filterString != null) {
     * String[] filterSpl = filterString.split(","); SearchCriteria[]
     * searchCriterias = new SearchCriteria[filterSpl.length]; for (int i = 0; i <
     * filterSpl.length; i++) { String tmp = filterSpl[i]; if (tmp != null) {
     * Pattern pattern = Pattern.compile("(\\w+?)(:|<|>)(\\w+?)-");//
     * (\w+?)(:|<|>)(\w+?), Matcher matcher = pattern.matcher(tmp + "-"); while
     * (matcher.find()) { LOG.info(matcher.group(0)); searchCriterias[i] = new
     * SearchCriteria(matcher.group(1), matcher.group(2), matcher.group(3)); } } }
     * sortAndFiltersInput = new SortAndFiltersInput(searchCriterias, null);
     * sortAndFiltersInput = CommonUtils.rebuildQueryDsl(DTOPerson.mapPair,
     * sortAndFiltersInput); LOG.info(sortAndFiltersInput.toString()); }
     * LOG.info("::getUsers, pages = " + page.toString()); Page<DPerson> pg =
     * userService.findAllPerson(page, sortAndFiltersInput); List<DTOPerson> lst =
     * new ArrayList<DTOPerson>(); List<DPerson> lPerson = pg.getContent(); lst =
     * lPerson.stream().map(p -> new DTOPerson(p)).collect(Collectors.toList());
     * Page<DTOPerson> dtoPerson = new PageImpl<DTOPerson>(lst, page,
     * pg.getTotalElements()); return ResponseEntity.ok().body(dtoPerson); }
     */
}
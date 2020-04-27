package com.hust.baseweb.repo;

import com.hust.baseweb.entity.Party;
import com.hust.baseweb.entity.PartyRelationship;
import com.hust.baseweb.entity.RoleType;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface PartyRelationshipRepo extends JpaRepository<PartyRelationship, UUID> {
    public PartyRelationship save(PartyRelationship partyRelationship);

    public List<PartyRelationship> findAllByFromPartyAndRoleTypeAndThruDate(Party fromParty,
                                                                            RoleType roleType,
                                                                            Date thruDate);

    public List<PartyRelationship> findAllByToPartyAndRoleTypeAndThruDate(Party toParty,
                                                                          RoleType roleType,
                                                                          Date thruDate);

    public List<PartyRelationship> findAllByFromPartyAndToPartyAndRoleTypeAndThruDate(Party fromParty,
                                                                                      Party toParty,
                                                                                      RoleType roleType,
                                                                                      Date thruDate);
}

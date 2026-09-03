package com.example.onlinevotingsystem.config;

import com.example.onlinevotingsystem.entity.Party;
import com.example.onlinevotingsystem.enums.PartyEnums.PartyStatus;
import com.example.onlinevotingsystem.enums.PartyEnums.PartySymbol;
import com.example.onlinevotingsystem.repository.PartyRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class PartyDataInitializer implements CommandLineRunner {

    @Autowired
    private PartyRepository partyRepository;

    @Override
    public void run(String... args) {

        createPartyIfNotExists(
                "BJP",
                PartySymbol.LOTUS,
                "MODI"
        );

        createPartyIfNotExists(
                "BRS",
                PartySymbol.CAR,
                "KCR"
        );

        createPartyIfNotExists(
                "CONGRESS",
                PartySymbol.HAND,
                "RAHUL"
        );

        createPartyIfNotExists(
                "TDP",
                PartySymbol.CYCLE,
                "CBN"
        );

        createPartyIfNotExists(
                "INDEPENDENT",
                PartySymbol.INDEPENDENT,
                "Independent"
        );
    }

    private void createPartyIfNotExists(
            String partyName,
            PartySymbol partySymbol,
            String founderName) {

        if (partyRepository.existsByPartyNameIgnoreCase(partyName)) {
            return;
        }

        if (partyRepository.existsByPartySymbol(partySymbol)) {
            return;
        }

        Party party = new Party();

        party.setPartyName(partyName);
        party.setPartySymbol(partySymbol);
        party.setFounderName(founderName);
        party.setStatus(PartyStatus.ACTIVE);

        LocalDateTime now = LocalDateTime.now();

        party.setRegisteredDate(now);
        party.setCreatedAt(now);
        party.setUpdatedAt(now);

        partyRepository.save(party);
    }
}
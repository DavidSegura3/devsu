package com.devsu.demo.ms.account.ms_account.config;

import com.devsu.demo.ms.account.ms_account.models.dtos.AccountDTO;
import com.devsu.demo.ms.account.ms_account.models.dtos.MovementDTO;
import com.devsu.demo.ms.account.ms_account.models.entities.Movement;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }

    @Bean("movementMapper")
    public ModelMapper movementMapper() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        TypeMap<Movement, MovementDTO> typeMapWrite = mapper.createTypeMap(Movement.class, MovementDTO.class);
        typeMapWrite.addMapping(Movement::getAccount, (dest, v) -> dest.setAccountDTO((AccountDTO) v));
        return mapper;
    }
}

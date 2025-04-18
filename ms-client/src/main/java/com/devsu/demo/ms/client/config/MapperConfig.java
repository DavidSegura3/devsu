package com.devsu.demo.ms.client.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MapperConfig {

    @Bean("defaultMapper")
    public ModelMapper defaultMapper(){
        return new ModelMapper();
    }

    /*@Bean("obligationMapper")
    public ModelMapper obligationMapper() {

        ModelMapper mapper = new ModelMapper();

        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<Obligation, ObligationDTO> typeMapWrite = mapper.createTypeMap(Obligation.class, ObligationDTO.class);
        typeMapWrite.addMapping(Obligation::getId, (dest, v) -> dest.setId((Long) v));
        typeMapWrite.addMapping(Obligation::getDescription, (dest, v) -> dest.setDescription((String) v));

        return mapper;
    }

    @Bean("contractMapper")
    public ModelMapper contractMapper() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<Contract, ContractDTO> typeMapWrite = mapper.createTypeMap(Contract.class, ContractDTO.class);
        typeMapWrite.addMapping(Contract::getFinalReceiptDate, (dest, v) -> dest.setFinalReceiptDate((Date) v));
        typeMapWrite.addMapping(Contract::getLiquidationDate, (dest, v) -> dest.setLiquidationDate((Date) v));
        typeMapWrite.addMapping(Contract::getFileClosureDate, (dest, v) -> dest.setFileClosureDate((Date) v));
        typeMapWrite.addMapping(Contract::getSubscriptionDate, (dest, v) -> dest.setSubscriptionDate((Date) v));
        typeMapWrite.addMapping(Contract::getCommencementDate, (dest, v) -> dest.setCommencementDate((Date) v));
        typeMapWrite.addMapping(Contract::getStartDate, (dest, v) -> dest.setStartDate((Date) v));
        typeMapWrite.addMapping(Contract::getEndDate, (dest, v) -> dest.setEndDate((Date) v));


        return mapper;
    }

    @Bean("contractTypeMapper")
    public ModelMapper contractTypeMapper() {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        TypeMap<ContractType, ContractTypeDTO> typeMapWrite = mapper.createTypeMap(ContractType.class, ContractTypeDTO.class);
        typeMapWrite.addMapping(ContractType::getName, (dest, v) -> dest.setName((String) v));

        return mapper;
    }*/
}

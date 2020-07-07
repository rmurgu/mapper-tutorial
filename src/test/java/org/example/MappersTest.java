package org.example;

import ma.glasnost.orika.MapperFacade;
import ma.glasnost.orika.MapperFactory;
import ma.glasnost.orika.impl.DefaultMapperFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.dozer.DozerBeanMapper;
import org.junit.Before;
import org.junit.Test;
import org.mapstruct.factory.Mappers;

import java.lang.reflect.InvocationTargetException;

public class MappersTest {

    private static final int MIL = 1_000_000;
    private ManualMapper manualMapper;
    private DozerBeanMapper dozerBeanMapper;
    private MapperFacade mapperFacade;
    private MapStructSrcDestMapper structMapper;

    @Before
    public void before() {
        // manual
        manualMapper = new ManualMapper();

        // dozer
        dozerBeanMapper = new DozerBeanMapper();

        // orika
        MapperFactory mapperFactory = new DefaultMapperFactory.Builder().build();
        mapperFacade = mapperFactory.getMapperFacade();

        // struct
        structMapper = Mappers.getMapper(MapStructSrcDestMapper.class);
    }

    @Test
    public void manualMapperMethod1() {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            manualMapper.mapSrcToDest(source);
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.MANUAL, start, finish);
    }

    @Test
    public void manualMapperMethod2() {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            manualMapper.mapSrcToDest(source, new Destination());
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.MANUAL, start, finish);
    }

    @Test
    public void dozerMapper() {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            dozerBeanMapper.map(source, Destination.class);
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.DOZER, start, finish);
    }

    @Test
    public void orikaMapper() {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            mapperFacade.map(source, Destination.class);
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.ORIKA, start, finish);
    }

    @Test
    public void structMapper() {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            structMapper.sourceToDestination(source);
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.STRUCT, start, finish);
    }

    @Test
    public void beanUtilsMapper() throws InvocationTargetException, IllegalAccessException {
        Source source = createSource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < MIL; i++) {
            BeanUtils.copyProperties(source, new Destination());
        }
        long finish = System.currentTimeMillis();
        printTimeSpent(Mapper.BEAN_UTILS, start, finish);
    }

    private Source createSource() {
        return new Source("TestName", 100);
    }

    private void printTimeSpent(Mapper mapper, long start, long finish) {
        System.out.println("Time spent mapping with " + mapper.name() + ": " + (finish - start) + " milliseconds");
        System.out.println("**********************************************************************");
    }

    enum Mapper {MANUAL, DOZER, ORIKA, STRUCT, BEAN_UTILS}
}

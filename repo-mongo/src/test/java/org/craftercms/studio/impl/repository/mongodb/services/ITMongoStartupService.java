package org.craftercms.studio.impl.repository.mongodb.services;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * Integration Testing for MongoStartupService service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repocitory.xml")
public class ITMongoStartupService implements ApplicationContextAware {

    private NodeService nodeService;
    private ApplicationContext applicationContext;

    @Before
    public void setUp() throws Exception {
        nodeService = applicationContext.getBean(NodeService.class);
    }

    @Test
    public void testRootFolderIsCreated() throws Exception {
        Assert.assertNotNull(nodeService.getRootNode());
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }



}

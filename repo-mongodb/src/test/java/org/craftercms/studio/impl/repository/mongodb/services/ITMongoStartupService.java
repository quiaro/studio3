package org.craftercms.studio.impl.repository.mongodb.services;

import java.util.List;

import org.craftercms.studio.impl.repository.mongodb.MongoRepositoryDefaults;
import org.craftercms.studio.impl.repository.mongodb.domain.Node;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.fail;

/**
 * Integration Testing for MongoStartupService service.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:/craftercms/studio/craftercms-mongo-repository.xml")
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


    @Test
    public void testContentNameCreated() throws Exception {
        List<Node> nodes = nodeService.findNodesByParent(nodeService.getRootNode());
        for (Node node : nodes) {
            if(node.getMetadata().getNodeName().equals(MongoRepositoryDefaults.REPO_DEFAULT_CONTENT_FOLDER)){
                return;
            }
        }

        fail("Content Folder not Found");
    }


    @Test
    public void testConfigNameCreated() throws Exception {
        List<Node> nodes = nodeService.findNodesByParent(nodeService.getRootNode());
        for (Node node : nodes) {
            if(node.getMetadata().getNodeName().equals(MongoRepositoryDefaults.REPO_DEFAULT_CONFIG_FOLDER)){
                return;
            }
        }
        fail("Config Folder not Found");
    }

    @Override
    public void setApplicationContext(final ApplicationContext applicationContext) throws BeansException {
        this.applicationContext=applicationContext;
    }



}

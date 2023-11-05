package com.example.web_marketplace;

import com.example.web_marketplace.controller.GoodsControllerTest;
import com.example.web_marketplace.controller.UserControllerTest;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.boot.test.context.SpringBootTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        GoodsControllerTest.class,
        UserControllerTest.class
})
class WebMarketplaceTest {


}

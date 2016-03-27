package component;

import com.mangofactory.swagger.configuration.SpringSwaggerConfig;
import com.mangofactory.swagger.models.dto.ApiInfo;
import com.mangofactory.swagger.paths.SwaggerPathProvider;
import com.mangofactory.swagger.plugin.EnableSwagger;
import com.mangofactory.swagger.plugin.SwaggerSpringMvcPlugin;
import jb.controller.BaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Created by Zhou Yibing on 2015/11/10.
 */
@Configuration
@EnableSwagger
public class MySwaggerConfig {

    private SpringSwaggerConfig springSwaggerConfig;

    /**
     * Required to autowire SpringSwaggerConfig
     */
    @Autowired
    public void setSpringSwaggerConfig(SpringSwaggerConfig springSwaggerConfig){
        this.springSwaggerConfig = springSwaggerConfig;
    }

    /**
     * Every SwaggerSpringMvcPlugin bean is picked up by the swagger-mvc
     * framework - allowing for multiple swagger groups i.e. same code base
     * multiple swagger resource listings.
     */
    @Bean
    public SwaggerSpringMvcPlugin customImplementation(){
        return new SwaggerSpringMvcPlugin(this.springSwaggerConfig).apiInfo(this.apiInfo())
                .includePatterns(new String[]{".apiSwgContractGuide.*?",".apiSwgHotGuide.*?",".apiSwgRecommend.*?",".apiSwgUserHobbyController.*?",".apiSwgBshootController.*?"})
                .ignoredParameterTypes(BaseController.class)
                .apiVersion("0.0.1").swaggerGroup("bshoot");
    }

    private ApiInfo apiInfo() {
        ApiInfo apiInfo = new ApiInfo("社交软件API","社交软件API","My Apps API terms of service",
                "zhouyibing_1990@163.com","My Apps API Licence Type","My Apps API Licence URL");
        return apiInfo;
    }

    class GtPaths extends SwaggerPathProvider {

        @Override
        protected String applicationPath() {
            return "/restapi";
        }
        @Override
        protected String getDocumentationPath() {
            return "/restapi";
        }
    }
}

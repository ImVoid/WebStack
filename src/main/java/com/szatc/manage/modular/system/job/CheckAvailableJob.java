package com.szatc.manage.modular.system.job;

import cn.hutool.core.util.ReUtil;
import cn.hutool.core.util.StrUtil;
import cn.hutool.http.HttpRequest;
import cn.hutool.http.HttpResponse;
import cn.hutool.http.HttpUtil;
import com.szatc.manage.core.common.node.MenuNode;
import com.szatc.manage.modular.system.dao.SiteMapper;
import com.szatc.manage.modular.system.model.Category;
import com.szatc.manage.modular.system.model.Site;
import com.szatc.manage.modular.system.service.impl.CategoryServiceImpl;
import com.szatc.manage.modular.system.service.impl.SiteServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Slf4j
@Component
public class CheckAvailableJob {

    @Autowired
    private CategoryServiceImpl categoryService;

    @Autowired
    private SiteServiceImpl siteService;

//    @Scheduled(cron = "0 0/5 * * * ?")
    public void check() {
        List<Category> categorySiteList = categoryService.getCatogrySite(null);

        for (Category category : categorySiteList) {
            for (Site site : category.getSites()) {
                Site updSite = new Site();
                updSite.setId(site.getId());

                String regex = site.getRegex();
                try {
                    log.info("{}进行有效性检测：{}", site.getTitle(), site.getUrl());
                    String html = getHtml(site.getUrl());
                    if (StrUtil.isNotBlank(regex)) {
                        boolean contain = ReUtil.contains(regex, html);
                        if (contain) {
                            updSite.setStatus(1);
                        } else {
                            updSite.setStatus(0);
                        }
                    } else {
                        updSite.setStatus(1);
                    }
                } catch (Exception e) {
                    updSite.setStatus(0);
                }


                siteService.update(updSite);
            }
        }
    }

    /**
     * 重定向至具体网址
     * @param url
     * @return
     */
    private String getHtml(String url) {
        String prefix = ReUtil.findAllGroup0("^(https|http|ftp)\\:\\/\\/[a-zA-Z0-9\\-\\.:]+", url).get(0);
        HttpResponse response = HttpRequest.get(url).execute();
        if (response.getStatus() == 302) {
            String location = response.header("Location");
            if (!location.contains("http")) {
                location = prefix + location;
            }
            return getHtml(location);
        } else {
            return response.body();
        }
    }
}

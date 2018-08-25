package com.publiccms.views.directive.cms;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.publiccms.common.base.AbstractTemplateDirective;
import com.publiccms.common.constants.CommonConstants;
import com.publiccms.common.handler.RenderHandler;
import com.publiccms.common.tools.CommonUtils;
import com.publiccms.entities.cms.CmsTagType;
import com.publiccms.entities.sys.SysSite;
import com.publiccms.logic.service.cms.CmsTagTypeService;

/**
 *
 * CmsTagTypeDirective
 * 
 */
@Component
public class CmsTagTypeDirective extends AbstractTemplateDirective {

    @Override
    public void execute(RenderHandler handler) throws IOException, Exception {
        Integer id = handler.getInteger("id");
        SysSite site = getSite(handler);
        if (CommonUtils.notEmpty(id)) {
            CmsTagType entity = service.getEntity(id);
            if (null != entity && site.getId() == entity.getSiteId()) {
                handler.put("object", entity).render();
            }
        } else {
            Integer[] ids = handler.getIntegerArray("ids");
            if (CommonUtils.notEmpty(ids)) {
                List<CmsTagType> entityList = service.getEntitys(ids);
                Map<String, CmsTagType> map = entityList.stream().filter(entity -> site.getId() == entity.getSiteId()).collect(Collectors.toMap(k -> k.getId().toString(),
                        Function.identity(), CommonConstants.defaultMegerFunction(), LinkedHashMap::new));
                handler.put("map", map).render();
            }
        }
    }

    @Autowired
    private CmsTagTypeService service;

}

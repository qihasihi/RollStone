package com.school.im1_1.manager.inter._interface;

import com.school.im1_1.entity._interface.ImInterfaceInfo;
import com.school.manager.base.IBaseManager;

import java.util.List;
import java.util.Map;

/**
 * Created by yuechunyang on 14-7-25.
 */
public interface IImInterfaceManager extends IBaseManager<ImInterfaceInfo> {
    public List<Map<String, Object>> getStudyModule(ImInterfaceInfo obj);
}

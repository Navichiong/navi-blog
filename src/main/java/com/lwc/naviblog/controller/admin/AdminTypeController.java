package com.lwc.naviblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.model.valid.Update;
import com.lwc.naviblog.service.TypeService;
import com.lwc.naviblog.utils.CommonResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/admin/type")
public class AdminTypeController {

    @Autowired
    private TypeService typeService;

    @GetMapping("/list")
    public CommonResult list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                             @RequestParam(required = false, defaultValue = "5") Integer navigatePages) {
        PageHelper.startPage(pageNum, pageSize, "id desc");
        List<Type> types = typeService.selectAll();
        PageInfo<Type> listPageInfo = new PageInfo<>(types, navigatePages);
        return CommonResultUtil.build(StatusCode.SUCCESS, "success", listPageInfo);
    }

    @GetMapping("/selectAll")
    public CommonResult selectAll(){
        List<Type> types = typeService.selectAll();
        return CommonResultUtil.build(StatusCode.SUCCESS, "success", types);
    }

    /**
     * 添加分类
     *
     * @param type 分类实体
     * @return Json描述类
     */
    @PostMapping("/addType")
    public CommonResult addType(@RequestBody @Validated(Insert.class) Type type) {
        // 查询是否存在该分类
        Type t = typeService.selectByName(type.getTypeName());
        if (t != null) {
            // 存在则不添加
            return CommonResultUtil.build(StatusCode.ERROR, "该分类已存在，无法添加", null);
        }
        return typeService.addType(type) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "添加分类成功", null) :
                CommonResultUtil.build(StatusCode.ERROR, "添加分类失败", null);
    }

    @GetMapping("/selectById/{id}")
    public CommonResult selectById(@PathVariable("id") Integer id) {
        Type type = typeService.selectById(id);
        return type != null ? CommonResultUtil.build(StatusCode.SUCCESS, "success", type) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @PutMapping("/updateById")
    public CommonResult updateById(@RequestBody @Validated(Update.class) Type type) {
        Type t = typeService.selectById(type.getId());
        if (t == null) {
            // 不存在则修改失败
            return CommonResultUtil.build(StatusCode.ERROR, type.getId() + "号分类不存在", null);
        }

        t = typeService.selectByName(type.getTypeName());
        if (t != null) {
            // 存在则不修改
            return CommonResultUtil.build(StatusCode.ERROR, "该分类已存在，修改失败", null);
        }
        return typeService.updateById(type) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @DeleteMapping("/deleteById/{id}")
    public CommonResult updateById(@PathVariable("id") Integer id) {
        Type type = typeService.selectById(id);
        if (type == null) {
            // 不存在则不删除
            return CommonResultUtil.build(StatusCode.ERROR, id + "号分类不存在", null);
        }
        return typeService.deleteById(id) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }
}

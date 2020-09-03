package com.lwc.naviblog.controller.admin;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwc.naviblog.enums.StatusCode;
import com.lwc.naviblog.model.CommonResult;
import com.lwc.naviblog.model.Tag;
import com.lwc.naviblog.model.Type;
import com.lwc.naviblog.model.valid.Insert;
import com.lwc.naviblog.model.valid.Update;
import com.lwc.naviblog.service.TagService;
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
@RequestMapping("/admin/tag")
public class AdminTagController {

    @Autowired
    private TagService tagService;

    @GetMapping("/list")
    public CommonResult list(@RequestParam(required = false, defaultValue = "1") Integer pageNum,
                             @RequestParam(required = false, defaultValue = "5") Integer pageSize,
                             @RequestParam(required = false, defaultValue = "5") Integer navigatePages) {
        PageHelper.startPage(pageNum, pageSize, "id desc");
        List<Tag> tags = tagService.selectAll();
        PageInfo<Tag> listPageInfo = new PageInfo<>(tags, navigatePages);
        return CommonResultUtil.build(StatusCode.SUCCESS, "success", listPageInfo);
    }

    @GetMapping("/selectAll")
    public CommonResult selectAll(){
        List<Tag> tags = tagService.selectAll();
        return CommonResultUtil.build(StatusCode.SUCCESS, "success", tags);
    }

    /**
     * 添加标签
     *
     * @param tag 标签实体
     * @return Json描述类
     */
    @PostMapping("/addTag")
    public CommonResult addTag(@RequestBody @Validated(Insert.class) Tag tag) {
        // 查询是否存在该标签
        Tag t = tagService.selectByName(tag.getName());
        if (t != null) {
            // 存在则不添加
            return CommonResultUtil.build(StatusCode.ERROR, "该标签已存在，无法添加", null);
        }
        return tagService.addTag(tag) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "添加标签成功", null) :
                CommonResultUtil.build(StatusCode.ERROR, "添加标签失败", null);
    }

    @GetMapping("/selectById/{id}")
    public CommonResult selectById(@PathVariable("id") Integer id) {
        Tag tag = tagService.selectById(id);
        return tag != null ? CommonResultUtil.build(StatusCode.SUCCESS, "success", tag) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @PutMapping("/updateById")
    public CommonResult updateById(@RequestBody @Validated(Update.class) Tag tag) {
        Tag t = tagService.selectById(tag.getId());
        if (t == null) {
            // 不存在则修改失败
            return CommonResultUtil.build(StatusCode.ERROR, tag.getId() + "号标签不存在", null);
        }

        t = tagService.selectByName(tag.getName());
        if (t != null) {
            // 存在则不修改
            return CommonResultUtil.build(StatusCode.ERROR, "该标签已存在，修改失败", null);
        }
        return tagService.updateById(tag) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }

    @DeleteMapping("/deleteById/{id}")
    public CommonResult updateById(@PathVariable("id") Integer id) {
        Tag tag = tagService.selectById(id);
        if (tag == null) {
            // 不存在则不删除
            return CommonResultUtil.build(StatusCode.ERROR, id + "号标签不存在", null);
        }
        return tagService.deleteById(id) > 0 ?
                CommonResultUtil.build(StatusCode.SUCCESS, "success", null) :
                CommonResultUtil.build(StatusCode.ERROR, "error", null);
    }
}

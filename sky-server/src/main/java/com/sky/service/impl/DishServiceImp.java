package com.sky.service.impl;


import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.sky.constant.StatusConstant;
import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.entity.DishFlavor;
import com.sky.exception.DeletionNotAllowedException;
import com.sky.mapper.DishFlavorMapper;
import com.sky.mapper.DishMapper;
import com.sky.mapper.SetmealDishMapper;
import com.sky.result.PageResult;
import com.sky.service.DishService;
import com.sky.vo.DishVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DishServiceImp implements DishService {

    @Autowired
    private DishMapper dishMapper;

    @Autowired
    private DishFlavorMapper dishFlavorMapper;
    @Transactional
    @Override
    public void saveWithFavor(DishDTO dishDTO) {
        Dish dish = new Dish();

        BeanUtils.copyProperties(dishDTO,dish);

        dishMapper.insert(dish);

        List<DishFlavor> flavors = dishDTO.getFlavors();
        if(flavors!=null&&flavors.size()>0){
            flavors.forEach((flavor)->{
                flavor.setDishId(dish.getId());
            });
            dishFlavorMapper.addBatch(flavors);
        }


    }


    @Override

    public PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO) {
        PageHelper.startPage(dishPageQueryDTO.getPage(),dishPageQueryDTO.getPageSize());
        Page<DishVO> dishes = dishMapper.pageQuery(dishPageQueryDTO);
        return new PageResult(dishes.getTotal(),dishes.getResult());
    }

    @Autowired
    private SetmealDishMapper setmealDishMapper;
    @Transactional
    @Override
    public void deleteBatch(Long[] ids) {
        for (Long id : ids) {
            Dish dish = dishMapper.getById(id);
            if(dish.getStatus()== StatusConstant.DISABLE){
                throw new DeletionNotAllowedException("不允许删除，有的菜品在禁用中");
            }
        }
        List<Long> setmeals = setmealDishMapper.getByDishIds(ids);
        if(setmeals!=null&&setmeals.size()>0){
            throw new DeletionNotAllowedException("不允许删除，菜品正在启售中");
        }
        dishMapper.deleteByIds(ids);
        dishFlavorMapper.deleteByDishId(ids);
    }
}

package com.sky.service;

import com.sky.dto.DishDTO;
import com.sky.dto.DishPageQueryDTO;
import com.sky.entity.Dish;
import com.sky.result.PageResult;
import com.sky.vo.DishVO;
import org.springframework.stereotype.Service;

import java.util.List;


public interface DishService {

    public void saveWithFavor(DishDTO dishDTO);

    PageResult pageQuery(DishPageQueryDTO dishPageQueryDTO);


    void deleteBatch(Long[] ids);

    DishVO getByIdWithFlavor(Long id);

    void updateWithFavor(DishDTO dishDTO);

    List<DishVO> listWithFlavor(Dish dish);
}

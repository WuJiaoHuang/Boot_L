package org.example.com.productservice.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.example.com.productservice.entity.Product;

/*
* 因为继承BaseMapper<Product>
所以天然拥有selectById,selectList,insert,updateById,deleteById*/
@Mapper
public interface ProductMapper extends BaseMapper<Product> {
}

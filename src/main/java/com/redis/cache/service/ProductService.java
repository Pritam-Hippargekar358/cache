package com.redis.cache.service;

import com.redis.cache.dto.Product;
import com.redis.cache.enums.CacheConstants;
import com.redis.cache.repository.ProductRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

@Service
@Slf4j
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    @Cacheable(value = CacheConstants.PRODUCT_CACHE, key = "#id", unless = "#result == null")
    public Optional<Product> get(Long id) {
        log.info("get request for product: {}",id);
        return productRepository.findById(id);
    }

    @CachePut(value = CacheConstants.PRODUCT_CACHE, key = "#product.id")
    public Product insert(Product product) {
        log.info("insert request for product: {}",product);
        return productRepository.save(product);
    }

    @CachePut(value = CacheConstants.PRODUCT_CACHE, key = "#id", unless = "#result == null")
    public Product update(Long id, Product updatedProduct) {
        return productRepository.findById(id)
                .map(existingProduct -> {
                    updatedProduct.setId(id);
                    return productRepository.save(updatedProduct);
                })
                .orElse(null);
    }

    @CacheEvict(value = CacheConstants.PRODUCT_CACHE, key = "#id")
    public boolean delete(Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return true;
        }
        return false;
    }

    @CacheEvict(value = { CacheConstants.PRODUCT_CACHE }, allEntries = true)
    public void deleteAll() {
        productRepository.deleteAll();
    }
}
//@Caching(
//        evict = {
//                @CacheEvict(value = "animalById", key = "#id"),
//                @CacheEvict(value = "animals", allEntries = true, beforeInvocation = true)
//        }
//)
//public ResponseEntity<Integer> deleteAnimalById(final int id){
//    return ResponseEntity.ok(animalRepository.deleteById(id));
//}


//@Component
//public class RedisKeyGenerator implements KeyGenerator {
//    @Override
//    public Object generate(Object o, Method method, Object... objects) {
//        return method.getName()+":"+ Arrays.toString(objects);
//    }
//}
//@Cacheable(cacheNames = "c1",keyGenerator = "redisKeyGenerator")
//public User getUserById1(Integer id,String name,String author) {
//    System.out.println("getUserId===>>>>" + id);
//    User u=new User();
//    u.setId(id);
//    return u;
//}

//@CachePut(cacheNames = "c1",key = "#user.id")
//public User updateUserById(User user){
//    return user;
//}
//@CacheEvict(cacheNames = "c1")
//public void deleteUserById(Integer id) {
//    System.out.println("deleteUserById==>>>>"+id);
//}
//Define the global cache name
//@CacheConfig(cacheNames = "c1")No need to add cacheName to each method
//@Service
//@CacheConfig(cacheNames = "c1")
//public class UserService {
//    @Cacheable(key = "#id+'-'+#name")
//    public User getUserById(Integer id,String name) {
//        User u=new User();
//        u.setId(id);
//        return u;
//    }
//}

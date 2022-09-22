package com.zhichanzaixian.trademarkapi.comm;


import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author syl  Date: 2018/12/24 Email:nerosyl@live.com
 */
@Data
@Accessors(chain = true)
public class BaseEntity implements Serializable {


    private Long id;

}

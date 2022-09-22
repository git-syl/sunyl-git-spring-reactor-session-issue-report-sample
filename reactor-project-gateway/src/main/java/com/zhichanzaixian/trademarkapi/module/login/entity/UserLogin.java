package com.zhichanzaixian.trademarkapi.module.login.entity;


import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.zhichanzaixian.trademarkapi.comm.BaseEntity;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityCoreVersion;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;

/**
 * <p>
 *
 * </p>
 *
 * @author syl
 * @since 2019-12-05
 */

@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
public class UserLogin extends BaseEntity implements UserDetails, CredentialsContainer {


    private static final long serialVersionUID = 1L;

   // @JsonEncrypt(beginIdx = 4, endIdx = 13)
    
    private String userId;

    
    private String userName;

    private String password;

    
    private String mobile;

    
    private String email;

    
    private Date expiredTime;

    
    private String qqOpenid;

    
    private String wechatUnionid;

    
    private String wechatOpenid;

    
    private String wechatOfficialAccountOpenid;

//    
    private String wecomUserId;


    @JsonSerialize(using= ToStringSerializer.class)

    private Boolean enabled;


    @JsonSerialize(using= ToStringSerializer.class)
    private Boolean locked;


    private String tenantId;

    private LocalDateTime updateTime;


    private String companyId;


    private LocalDateTime lastLoginTime;

    //user-detail ,employee的read_only
    private Boolean tenantDefaultAccount;


    private Set<GrantedAuthority> authorities;



    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getExpiredTime() {
        return expiredTime;
    }

    public void setExpiredTime(Date expiredTime) {
        this.expiredTime = expiredTime;
    }

    public String getQqOpenid() {
        return qqOpenid;
    }

    public void setQqOpenid(String qqOpenid) {
        this.qqOpenid = qqOpenid;
    }

    public String getWechatUnionid() {
        return wechatUnionid;
    }

    public void setWechatOfficialAccountOpenid(String wechatOfficialAccountOpenid) {
        this.wechatOfficialAccountOpenid = wechatOfficialAccountOpenid;
    }

    public String getWechatOpenid() {
        return wechatOpenid;
    }

    public void setWechatOpenid(String wechatOpenid) {
        this.wechatOpenid = wechatOpenid;
    }

    public String getWechatOfficialAccountOpenid() {
        return wechatOfficialAccountOpenid;
    }

    public String getWecomUserId() {
        return wecomUserId;
    }

    public void setWecomUserId(String wecomUserId) {
        this.wecomUserId = wecomUserId;
    }

    public void setWechatUnionid(String wechatUnionid) {
        this.wechatUnionid = wechatUnionid;
    }
    //    public Boolean getEnabled() {
//        return enabled;
//    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public Boolean getLocked() {
        return locked;
    }

    public void setLocked(Boolean locked) {
        this.locked = locked;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public LocalDateTime getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(LocalDateTime lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Boolean getTenantDefaultAccount() {
        return tenantDefaultAccount;
    }

    public void setTenantDefaultAccount(Boolean tenantDefaultAccount) {
        this.tenantDefaultAccount = tenantDefaultAccount;
    }

    public void setAuthorities(Set<GrantedAuthority> authorities) {
        this.authorities = authorities;
    }



    @Override
    public void eraseCredentials() {
        this.password =null;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    public void initAuthorities(Collection<? extends GrantedAuthority> authorities) {
        this.authorities = Collections.unmodifiableSet(sortAuthorities(authorities));
        ;
    }

    @Override
    public String getUsername() {
        return userName;
    }

    @Override
    public boolean isAccountNonExpired() {
        if (expiredTime == null) {
            return true;
        }
        return expiredTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate().isAfter(LocalDate.now());
    }


    @Override
    public boolean isAccountNonLocked() {
        if (locked==null){
            return true;
        }
        return !locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }


    @Override
    public boolean isEnabled() {
        if (enabled==null){
            return false;
        }
        return enabled;
    }

    private static SortedSet<GrantedAuthority> sortAuthorities(
            Collection<? extends GrantedAuthority> authorities) {
        Assert.notNull(authorities, "Cannot pass a null GrantedAuthority collection");
        // Ensure array iteration order is predictable (as per
        // UserDetails.getAuthorities() contract and SEC-717)
        SortedSet<GrantedAuthority> sortedAuthorities = new TreeSet<>(
                new AuthorityComparator());

        for (GrantedAuthority grantedAuthority : authorities) {
            Assert.notNull(grantedAuthority,
                    "GrantedAuthority list cannot contain any null elements");
            sortedAuthorities.add(grantedAuthority);
        }

        return sortedAuthorities;
    }

    private static class AuthorityComparator implements Comparator<GrantedAuthority>,
            Serializable {
        private static final long serialVersionUID = SpringSecurityCoreVersion.SERIAL_VERSION_UID;

        @Override
        public int compare(GrantedAuthority g1, GrantedAuthority g2) {
            // Neither should ever be null as each entry is checked before adding it to
            /// the set.
            // If the authority is null, it is a custom authority and should precede
            // others.
            if (g2.getAuthority() == null) {
                return -1;
            }

            if (g1.getAuthority() == null) {
                return 1;
            }

            return g1.getAuthority().compareTo(g2.getAuthority());
        }
    }

//    public static void main(String[] args) {
//        UserLogin userLogin = new UserLogin();
//        userLogin.initAuthorities(AuthorityUtils.commaSeparatedStringToAuthorityList("record:delete"));
//
//        System.out.println(
//
//                userLogin.hasAuthority("record:delete22"));
//    }

    //todo:
    public boolean hasAuthority(String code) {

        if (authorities == null) {
            return false;
        }

      return   authorities.contains(new SimpleGrantedAuthority(code));



    }


}

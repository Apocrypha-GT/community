package com.study.community.dto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

//存储从GitHub获取的用户信息
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubUser {
    private String name;
    private Long id;
    private String bio;
    private String avatar_url;
}

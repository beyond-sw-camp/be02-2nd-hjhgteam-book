package com.example.demo.follow.service;

import com.example.demo.follow.model.Follow;
import com.example.demo.follow.model.dto.request.FollowReq;
import com.example.demo.follow.repository.FollowRepository;
import com.example.demo.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class FollowService {
    private final MemberRepository memberRepository;
    private final FollowRepository followRepository;

    public void followMember(FollowReq memberFollowReq) {

        int followState = followRepository.followState(memberFollowReq.getFollower_id(), memberFollowReq.getFollowing_id());
        if (followState == 0) {
            followRepository.save(Follow.builder()
                    .follower_id(memberFollowReq.getFollower_id())
                    .following_id(memberFollowReq.getFollowing_id()).build());
        }else {
            followRepository.unFollow(memberFollowReq.getFollower_id(), memberFollowReq.getFollowing_id());
        }
    }

    private List<String> getNickNames(List<Long> userIds) {
        List<String> nicknameList = new ArrayList<>();
        for (Long id : userIds) {
            String result = memberRepository.getNickName(id);
            if (!result.isEmpty()) {
                nicknameList.add(result);
            }
        }
        return nicknameList;
    }

    public List<String> showFollowings(Long userId) {
        List<Long> followings = followRepository.followings(userId);
        return getNickNames(followings);
    }

    public List<String> showFollowers(Long userId) {
        List<Long> followers = followRepository.followers(userId);
        return getNickNames(followers);
    }


}

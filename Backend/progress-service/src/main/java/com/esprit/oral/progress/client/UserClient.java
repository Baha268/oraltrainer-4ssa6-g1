package com.esprit.oral.progress.client;

import java.util.List;

/**
 * Access to user-service data (GET /api/users/{id}/goal, GET /api/groups/{id}/members).
 * S6: replace StubUserClient with an OpenFeign client behind this interface;
 * Feign failures must be mapped to RemoteServiceUnavailableException (HTTP 503).
 */
public interface UserClient {

    /** Target CEFR level of the learner. */
    String getTargetLevel(Long userId);

    /** User ids of the members of a class. */
    List<Long> getGroupMembers(Long groupId);
}

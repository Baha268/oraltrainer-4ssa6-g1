package com.esprit.oral.progress.client;

import com.esprit.oral.progress.repository.ProgressSnapshotRepository;
import java.util.List;
import org.springframework.stereotype.Component;

/**
 * TEMPORARY placeholder until the Feign client (S6) exists.
 * Target level is fixed to C1 and every learner with a snapshot is treated as a member of any group.
 */
@Component
public class StubUserClient implements UserClient {

    private final ProgressSnapshotRepository snapshots;

    public StubUserClient(ProgressSnapshotRepository snapshots) {
        this.snapshots = snapshots;
    }

    @Override
    public String getTargetLevel(Long userId) {
        return "C1";
    }

    @Override
    public List<Long> getGroupMembers(Long groupId) {
        return snapshots.findDistinctUserIds();
    }
}

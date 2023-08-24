package dmitry.adm.entity.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.io.Serializable;

@Getter
@AllArgsConstructor

@Entity
@Table(name = "group_member")
@IdClass(GroupMember.PrimaryKey.class)
public class GroupMember {

    @EqualsAndHashCode
    public static final class PrimaryKey implements Serializable {

        private int groupId;
        private int memberId;
    }

    @Id
    @Column(name = "group_id")
    private int groupId;

    @Id
    @Column(name = "member_id")
    private int memberId;

    protected GroupMember() {
    }
}

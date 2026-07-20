package com.rakesh.razorpay.operations.entity;

import com.rakesh.razorpay.common.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "settlement_payment")
public class SettlementPayment extends BaseEntity {
    /*
    @EmbeddedId
    Composite key logically belongs together.
    You want the key represented as a single object.
    Common in many-to-many join tables with extra columns.
     */
    @EmbeddedId
    private SettlementPaymentId id;

    /*
    @MapsId is used to map a foreign key relationship to a primary key field.
    It is commonly used in shared primary key one-to-one mappings and composite key mappings with @EmbeddedId.
    It allows JPA to automatically copy the ID from the associated entity into the primary key,
    avoiding manual synchronization of foreign key and primary key values.
     */

    @MapsId("settlementId")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "settlement_id", nullable = false )
    private Settlement settlement;

}

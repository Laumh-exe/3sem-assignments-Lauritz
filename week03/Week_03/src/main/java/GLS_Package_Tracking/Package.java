package GLS_Package_Tracking;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "package")
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Package {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "package_id")
    @ToString.Exclude
    private int id;

    @Column(name = "tracking_number")
    private String trackingNumber;

    @Column(name = "sender_name")
    private String senderName;

    @Column(name = "receiver_name")
    private String receiverName;

    @Enumerated(EnumType.STRING)
    @Column(name = "delivery_status")
    private DeliveryStatus deliveryStatus;

    @Column(name = "date_created")
    @Setter(AccessLevel.NONE)
    private LocalDateTime dateCreated;

    @Setter(AccessLevel.NONE)
    @Column(name = "date_modified")
    private LocalDateTime dateModified;

    public Package(String trackingNumber, String senderName, String receiverName) {
        this.trackingNumber = trackingNumber;
        this.senderName = senderName;
        this.receiverName = receiverName;
    }

    @PrePersist
    private void setDateCreated(){
        this.deliveryStatus = DeliveryStatus.PENDING;
        this.dateCreated = LocalDateTime.now();
        this.dateModified = LocalDateTime.now();
    }

    @PreUpdate()
    private void setDateModified() {
        this.dateModified = LocalDateTime.now();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null) return false;
        Package aPackage = (Package) o;
        return id == aPackage.id && Objects.equals(trackingNumber, aPackage.trackingNumber) && Objects.equals(senderName, aPackage.senderName) && Objects.equals(receiverName, aPackage.receiverName) && deliveryStatus == aPackage.deliveryStatus;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, trackingNumber, senderName, receiverName, deliveryStatus, dateCreated, dateModified);
    }
}


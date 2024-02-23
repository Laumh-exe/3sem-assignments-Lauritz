package exercise4.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@Table(name = "shipment")
public class Shipment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shipment_id")
    private int id;

    @Column(name = "shipment_date")
    @Temporal(TemporalType.TIMESTAMP)
    private LocalDateTime shipmentDate;

    @ManyToOne
    private Package packageObj;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "source_location_id")
    private Location location;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "destination_location_id")
    private Location destinationLocation;

    public Shipment(LocalDateTime shipmentDate, Location location, Location destinationLocation) {
        this.shipmentDate = shipmentDate;
        this.location = location;
        this.destinationLocation = destinationLocation;
    }

    public void addPackage(Package packageObj) {
        this.packageObj = packageObj;
        if(!packageObj.getShipments().contains(this)) {
            packageObj.getShipments().add(this);
        }
    }


}
//Task1
const names = ["John", "James", "Jane", "Harry", "Helen", "Sigrfid", "Bo", "Bob"];
const namesResult = names.filter((name) => name.length <= 3);

console.log("Original: " + names);
console.log("result: " + namesResult);

//Task2 
const namesMapped = names.map((name) => name.toUpperCase());

console.log("\nOriginal: " + names);
console.log("result: " + namesMapped);

//Task3
console.log(returnHTML(names));

function returnHTML(names) {
    let html = "<ul>";
    names = names.map((name) => "<li>" + name + "</li>");
    html += names.join();
    html += "<ul>";
    return html;
}

//Task4
let cars = [
    { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
    { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
    { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
    { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
    { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];

console.log("Cars newer than 1999: ",cars.filter(car => car.year>1999));
console.log("Only Volvo: ",cars.filter(car => car.make === "Volvo"));
console.log("Cars that cost less than 5000: ",cars.filter(car => car.price < 5000));

//task5
function returnInsertSQLCars(cars) {
    sql = "INSERT INTO cars (id,year,make,model,price) VALUES ";
    cars = cars.map((car) => "( id: " + car.id + ", year: "+ car.year + ", make: '"+ car.make + "', model: '"+ car.model + "', price: "+ car.price + ")");
    sql += cars.join(",");
    return sql;
}

console.log(returnInsertSQLCars(cars));
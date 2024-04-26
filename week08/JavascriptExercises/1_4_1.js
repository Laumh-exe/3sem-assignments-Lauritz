//1.4.1
let div1 = document.getElementById("div1");
let div2 = document.getElementById("div2");

//1.4.2
div1.onclick = function changeDivText() {
    console.log("Hi from idOfTheDiv");
    paragraph.innerText = "Hi from idOfTheDiv";
}
div2.onclick = function changeDivText() {
    console.log("Hi from idOfTheDiv");
    paragraph.innerText = "Hi from idOfTheDiv";
}

//1.4.5
let divOuter = document.getElementById("outer");
document.getElementById("outer").addEventListener("click", function (e) {
    paragraph.innerText = e.target.id;
});

//1.4.6
let paragraph = document.getElementById("paragraph");

//1.5.1

let names = ["John", "James", "Jane", "Harry", "Helen", "Sigrfid", "Bo", "Bob"];

let nameList = document.getElementById("names");
nameList.innerHTML = returnHTML(names);

function returnHTML(names) {
    let html = "<ul>\n";
    names = names.map((name) => "<li>" + name + "</li>\n");
    html += names.join("");
    html += "<ul>";
    return html;
}

//1.5.2
const form = document.getElementById("form");

form.addEventListener("submit", function (e) {
    e.preventDefault();
    const name = document.getElementById("fname").value;
    names.push(name);
    nameList.innerHTML = returnHTML(names);
})

//1.5.3
const removeFirstButton = document.getElementById("removeFirst");
removeFirstButton.addEventListener("click", function (e) {
    e.preventDefault();
    names.shift();
    nameList.innerHTML = returnHTML(names);
})
const removeLastButton = document.getElementById("removeLast");
removeLastButton.addEventListener("click", function (e) {
    e.preventDefault();
    names.pop();
    nameList.innerHTML = returnHTML(names);
})

//1.6.1
var cars = [
    { id: 1, year: 1997, make: 'Ford', model: 'E350', price: 3000 },
    { id: 2, year: 1999, make: 'Chevy', model: 'Venture', price: 4900 },
    { id: 3, year: 2000, make: 'Chevy', model: 'Venture', price: 5000 },
    { id: 4, year: 1996, make: 'Jeep', model: 'Grand Cherokee', price: 4799 },
    { id: 5, year: 2005, make: 'Volvo', model: 'V70', price: 44799 }
];

createCarTable(cars);

function createCarTable(cars) {
    const newTable = document.createElement("table");
    newTable.innerHTML = "<tr><th>id</th><th>year</th><th>make</th><th>model</th><th>price</th></tr>";
    cars.map((car) => {
        const newRow = document.createElement("tr");
        const tdId = document.createElement("td");
        const tdYear = document.createElement("td");
        const tdMake = document.createElement("td");
        const tdModel = document.createElement("td");
        const tdPrice = document.createElement("td");
        tdId.textContent = car.id;
        tdYear.textContent = car.year;
        tdMake.textContent = car.make;
        tdModel.textContent = car.model;
        tdPrice.textContent = car.price;
        newRow.appendChild(tdId);
        newRow.appendChild(tdYear);
        newRow.appendChild(tdMake);
        newRow.appendChild(tdModel);
        newRow.appendChild(tdPrice);
        newTable.appendChild(newRow);
    })

    const tableDiv = document.getElementById("carTable");
    tableDiv.appendChild(newTable);
}

//1.6.2
const submitPrice = document.getElementById("carForm");
submitPrice.addEventListener("submit", function (e) {
    e.preventDefault();
    let price = document.getElementById("fcarPrice").value;
    console.log(price);
    const carsCopy = cars.filter(car => car.price <= price);
    createCarTable(carsCopy);
})

//1.7
const buttons = document.getElementById("buttons");
const display = document.getElementById("display");
let lastButtonEquals = false;
buttons.addEventListener("click", function (e) {
    if (!e.target.classList.contains("t1")) {
        return;  // Not a button, so exit the function
    }

    e.preventDefault();
    const buttonContent = e.target.innerHTML;
    let displayContent = display.innerHTML;
    const symbols = ["/", "*", "-", ".", "+"];
    
        if (lastButtonEquals) {
            display.innerHTML = "";
            lastButtonEquals = false;
        }
        if (buttonContent === "=") {
            const result = eval(display.innerHTML);
            display.innerHTML = result;
            lastButtonEquals = true;
        }
        else if ((!symbols.includes(displayContent.charAt(displayContent.length - 1)) || !symbols.includes(buttonContent))) {
            display.innerHTML += buttonContent;
        }
    
})

//1.1.1
let names = ["Lars", "Peter", "Jan", "Bo"];

//1.1.2 
function myfilter(names, callback) {
    return names.filter(firstName => callback(firstName));
}

function checkName(element) {
    if (element.length >= 3) {
        return true;
    }
    return false;
}

console.log(myfilter(names, checkName));

//task 1.1.3
function myMap(names, callback) {
    return names.map(firstName => changeName(firstName));
}

function changeName(element) {
    return element.toUpperCase();
}

console.log(myMap(names));

//1.2
Array.prototype.filterNames = function myfilter(callback) {
    return this.filter(element => callback(element));
}


let names2 = ["Lars", "Peter", "Jan", "Bo"];
let newArray = names2.filterNames(checkName);
console.log("Filter names based on array prototype function: " + newArray);


Array.prototype.mapNames = function (callback) {
    return names.map(firstName => changeName(firstName));
}

newArray = names2.mapNames(changeName);
console.log("Map names based on array prototype function: " + newArray);

//1.3.1
let divArr = document.getElementsByTagName("div");
divArr[0].style.backgroundColor = "red";
divArr[1].style.backgroundColor = "green";
divArr[2].style.backgroundColor = "yellow";

//1.3.2

let button1 = document.getElementById("button1");
button1.onmousedown = function changeColorOfDivs() {
    divArr[0].style.backgroundColor = "green";
    divArr[1].style.backgroundColor = "yellow";
    divArr[2].style.backgroundColor = "red";
}

button1.onmouseup = function changeColorOfDivs() {
    divArr[0].style.backgroundColor = "red";
    divArr[1].style.backgroundColor = "green";
    divArr[2].style.backgroundColor = "yellow";
}

//1.4.1
//check  1_4_1.js
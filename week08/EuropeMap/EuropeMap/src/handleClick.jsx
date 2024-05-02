const handleCountryClick = (target, {lastClicked}) => {
  if (lastClicked) {
    lastClicked.setAttribute("fill", "silver");
  }
  console.log("Country clicked: ", target.id);
  target.setAttribute("fill", "red");
  setLastClicked(target)
  setCurrentCountry(target.id)
  //Call on compoonent that shows details of country
};

export default handleCountryClick;
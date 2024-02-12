document.addEventListener("DOMContentLoaded", function() {

    const root = document.querySelector("#root");

    //sectiunea pentru introducerea orasului
    const titleSearchBox = document.createElement("h2");
    titleSearchBox.textContent = "Introduceți numele orașului";

    const containerSearchBox = document.createElement("div");
    containerSearchBox.className = "whiteContainerWithContentCentered";

    root.append(containerSearchBox);
    containerSearchBox.append(titleSearchBox);

    // input box-ul + button submit + spinner
    const divSearchBox = document.createElement("div");
    divSearchBox.className = "flexboxWithAlignItemsCentered";

    const divCityInputWithSuggestions = document.createElement("div");
    divCityInputWithSuggestions.style.margin = "20px";

    const cityInput = document.createElement("input");
    cityInput.id = "cityInput";
    divCityInputWithSuggestions.append(cityInput);
    
    const submitCityButton = document.createElement("button");
    submitCityButton.textContent = "Submit";
    submitCityButton.className = "actionButton";
    submitCityButton.addEventListener("click",function(){

        buttonCityFavoriteCurrentState = 0;
        buttonCityFavorite.src = buttonCityFavoriteState[0];

        if(favoriteCities.findIndex(city => city.name == cityInput.value && city.lon == cityInput.dataset.lon && city.lat == cityInput.dataset.lat) != -1){
            buttonCityFavoriteCurrentState = 1;
            buttonCityFavorite.src = buttonCityFavoriteState[1];
        }

        titleWeatherBox.textContent = cityInput.value;
        titleWeatherBox.dataset.lat = cityInput.dataset.lat;
        titleWeatherBox.dataset.lon = cityInput.dataset.lon;
        buttonHowLong.textContent = "For now";
        showWeatherNow(cityInput);
        changeBackground(cityInput.value);
    })

    const spinnerSearchBox = document.createElement("div");
    spinnerSearchBox.className = "spinner";
    spinnerSearchBox.hidden = true;

    divSearchBox.append(divCityInputWithSuggestions);
    divSearchBox.append(submitCityButton);
    divSearchBox.append(spinnerSearchBox);

    containerSearchBox.append(divSearchBox);

    let suggestionsContainer = document.createElement("ul");
    suggestionsContainer.id = "suggestionsContainer";
    cityInput.after(suggestionsContainer);
    let timeout;

    //Sectiunea de va arata vremea
    const divWeatherBox = document.createElement("div");
    divWeatherBox.className = "whiteContainer";

    const headerWeatherBox = document.createElement("div");
    headerWeatherBox.className = "flexboxWithAlignItemsCentered";
    headerWeatherBox.style.margin = "10px";
    headerWeatherBox.style.marginBottom = "0px";

    const titleWeatherBox = document.createElement("h2");

    const buttonCityFavorite = document.createElement("img");
    buttonCityFavorite.className = "favoriteButton";
    const buttonCityFavoriteState = ["./assets/img/favButtonNeSelectat.jpg", "./assets/img/favButtonSelectat.jpg"];
    let buttonCityFavoriteCurrentState = 0;
    buttonCityFavorite.src = buttonCityFavoriteState[buttonCityFavoriteCurrentState];

    buttonCityFavorite.addEventListener("mouseover", function(){
        if (buttonCityFavoriteCurrentState == 0){
            buttonCityFavorite.src = buttonCityFavoriteState[1];
        }
        else{
            buttonCityFavorite.src = buttonCityFavoriteState[0]
        }
        buttonCityFavorite.style.cursor = "pointer";
    });

    buttonCityFavorite.addEventListener("mouseleave", function(){
        buttonCityFavorite.src = buttonCityFavoriteState[buttonCityFavoriteCurrentState];
    });

    buttonCityFavorite.addEventListener("click",function(){
        if(buttonCityFavoriteCurrentState == 0){
            buttonCityFavoriteCurrentState = 1;
            let favoriteCity = {};
            favoriteCity.name = titleWeatherBox.textContent;
            favoriteCity.lon = titleWeatherBox.dataset.lon;
            favoriteCity.lat = titleWeatherBox.dataset.lat;
            favoriteCities.push(favoriteCity);
            localStorage.setItem("favoriteCities",JSON.stringify(favoriteCities));
            const option = createOptionForFavoriteCities(favoriteCity);
            favoritesSelectElement.append(option);
        }
        else{
            buttonCityFavoriteCurrentState = 0;
            let index = favoriteCities.findIndex(city => city.name == titleWeatherBox.textContent && city.lon == titleWeatherBox.dataset.lon && city.lat == titleWeatherBox.dataset.lat);
            favoriteCities.splice(index,1);
            localStorage.setItem("favoriteCities",JSON.stringify(favoriteCities));
            for(let i = 0; i < favoritesSelectElement.options.length; i++){
                let option = favoritesSelectElement.options[i];
                if(option.textContent == titleWeatherBox.textContent && option.dataset.lat == titleWeatherBox.dataset.lat && option.dataset.lon == titleWeatherBox.dataset.lon){
                    option.remove();
                    break;
                }
            }
        }
    });

    const buttonHowLong = document.createElement("button");
    buttonHowLong.className = "actionButton";
    buttonHowLong.textContent = "For now";
    buttonHowLong.addEventListener("click",function() {
        if(buttonHowLong.textContent == "For now"){
            showWeatherFor5Days();
            buttonHowLong.textContent = "For 5 Days";
        }
        else{
            showWeatherNow(titleWeatherBox);
            buttonHowLong.textContent = "For now";
        }
    });

    const spinnerWeather = document.createElement("div");
    spinnerWeather.className = "spinner";
    spinnerWeather.hidden = true;

    const favoritesContainer = document.createElement("div");
    favoritesContainer.style.marginLeft = "auto";
    favoritesContainer.textContent = "favorites: "
    const favoritesSelectElement = document.createElement("select");
    favoritesContainer.append(favoritesSelectElement);
    const defaultOption = document.createElement("option");
    defaultOption.textContent = "  ------  ";
    favoritesSelectElement.append(defaultOption);

    favoritesSelectElement.addEventListener("change",function(){
        let selectedOption = favoritesSelectElement.options[favoritesSelectElement.selectedIndex];
        buttonCityFavoriteCurrentState = 0;
        buttonCityFavorite.src = buttonCityFavoriteState[0];
        if(selectedOption.textContent != "  ------  "){
            titleWeatherBox.textContent = selectedOption.textContent;
            titleWeatherBox.dataset.lat = selectedOption.dataset.lat;
            titleWeatherBox.dataset.lon = selectedOption.dataset.lon;
            buttonCityFavoriteCurrentState = 1;
            buttonCityFavorite.src = buttonCityFavoriteState[1];
            buttonHowLong.textContent = "For now";
            showWeatherNow(selectedOption);
            changeBackground(selectedOption.textContent);
        }
    });

    let favoriteCities = [];

    function createOptionForFavoriteCities(city){
        const option = document.createElement("option");
        option.textContent = city.name;
        option.dataset.lon = city.lon;
        option.dataset.lat = city.lat;
        return option;
    }

    if(localStorage.getItem("favoriteCities")){
        favoriteCities = JSON.parse(localStorage.getItem("favoriteCities"));
        favoriteCities.forEach(city => {
            const option = createOptionForFavoriteCities(city);
            favoritesSelectElement.append(option);
        });
    }

    headerWeatherBox.append(titleWeatherBox);
    headerWeatherBox.append(buttonCityFavorite);
    headerWeatherBox.append(buttonHowLong);
    headerWeatherBox.append(spinnerWeather);
    headerWeatherBox.append(favoritesContainer);
    divWeatherBox.append(headerWeatherBox);

    const weatherWidgetsContainer = document.createElement("div");
    weatherWidgetsContainer.id = "weatherWidgetsContainer";

    divWeatherBox.append(weatherWidgetsContainer);
    let weatherBoxIntroducedInRoot = false;

    //crearea unui widget de vreme
    function createWidget(){
        const weatherWidget = document.createElement("div");
        weatherWidget.className = "weatherWidget";

        const imageDescriptionFrame = document.createElement("div");
        const imageDescription = document.createElement("img");
        imageDescriptionFrame.append(imageDescription);
        imageDescriptionFrame.className = "flexboxCentered";
        imageDescription.src = "//cdn.weatherapi.com/weather/64x64/day/143.png"; //default value

        const weatherDescriptionWithDateContainer = document.createElement("div");
        weatherDescriptionWithDateContainer.className = "weatherDescriptionWithDateContainer";

        const weatherDescription = document.createElement("div");
        weatherDescription.className = "weatherDescriptionContainerItem grid1fr2fr";
        weatherDescription.style.background = "#2b2a27";

        const temperatureInfo = document.createElement("p");
        temperatureInfo.className = "info flexboxCentered";
        temperatureInfo.style.fontSize = "50px";
        temperatureInfo.textContent = "24°"; //default
        weatherDescription.append(temperatureInfo);

        const summaryInfo = document.createElement("div");
        summaryInfo.className = "flexboxCentered";
        summaryInfo.style.flexDirection = "column";

        const pForSummaryInfo = document.createElement("p");
        pForSummaryInfo.className = "info";
        pForSummaryInfo.style.fontSize = "20px";
        pForSummaryInfo.textContent = "Cloudy";//default
        summaryInfo.append(pForSummaryInfo);

        const pForCity = document.createElement("p");
        pForCity.className = "info";
        pForCity.style.fontSize = "10px";
        pForCity.textContent = "default";
        summaryInfo.append(pForCity);

        weatherDescription.append(summaryInfo);

        const weatherDateContainer = document.createElement("div");
        weatherDateContainer.className = "weatherDescriptionContainerItem flexboxCentered";
        weatherDateContainer.style.background = "#56d168";

        const weatherDateInfo = document.createElement("p");
        weatherDateInfo.className = "info";
        weatherDateInfo.style.fontSize = "35px";
        weatherDateInfo.textContent = "1 JAN"; //default
        weatherDateContainer.append(weatherDateInfo);

        weatherDescriptionWithDateContainer.append(weatherDescription);
        weatherDescriptionWithDateContainer.append(weatherDateContainer);

        weatherWidget.append(imageDescriptionFrame);
        weatherWidget.append(weatherDescriptionWithDateContainer);

        return weatherWidget;
    }

    async function showWeatherFor5Days(){
        spinnerWeather.hidden = false;
        let result;
        const months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
        while(weatherWidgetsContainer.firstChild){
            weatherWidgetsContainer.removeChild(weatherWidgetsContainer.firstChild);
        }
        try{
            if (titleWeatherBox.dataset.lon != "null" && titleWeatherBox.dataset.lat != "null"){
                // this will expire
                const response = await fetch(`http://api.weatherapi.com/v1/forecast.json?key=4b26c4d8e83642b588e105638241202&q=${titleWeatherBox.dataset.lat},${titleWeatherBox.dataset.lon}&days=5&aqi=no&alerts=no`);
                if(response.ok){
                    result = await response.json();
                }
                else{
                    alert("Nu a mers fetch-ul pentru coordonate");
                }
            }
            else{
                // api key to expire
                const response = await fetch(`http://api.weatherapi.com/v1/forecast.json?key=4b26c4d8e83642b588e105638241202&q=${titleWeatherBox.textContent}&days=5&aqi=no&alerts=no`);
                if(response.ok){
                    result = await response.json();
                }
                else{
                    alert("Nu ati introdus un oras valid");
                }
            }
        }
        catch(error){
            alert(error);
        }
        let currentDate = new Date();
        for(let i = 0; i < 5; i++){
            const widgetNow = createWidget();
            const imageDescription = widgetNow.firstElementChild.firstElementChild;
            const temperatureInfo = widgetNow.children[1].firstElementChild.firstElementChild;
            const pSummaryInfo = widgetNow.children[1].firstElementChild.children[1].firstElementChild;
            const pCityName = widgetNow.children[1].firstElementChild.children[1].children[1];
            const date = widgetNow.children[1].children[1].firstElementChild;
            imageDescription.src = result.forecast.forecastday[i].day.condition.icon;
            temperatureInfo.textContent = result.forecast.forecastday[i].day.avgtemp_c + "°";
            pSummaryInfo.textContent = result.forecast.forecastday[i].day.condition.text;
            pCityName.textContent = result.location.name;
            date.textContent = currentDate.getDate() + " " + months[currentDate.getMonth()];
            currentDate.setDate(currentDate.getDate() + 1);
            weatherWidgetsContainer.append(widgetNow);
        }
        spinnerWeather.hidden = true;
    }


    async function showWeatherNow(node){
        spinnerWeather.hidden = false;
        const months = ["JAN","FEB","MAR","APR","MAY","JUN","JUL","AUG","SEP","OCT","NOV","DEC"];
        while(weatherWidgetsContainer.firstChild){
            weatherWidgetsContainer.removeChild(weatherWidgetsContainer.firstChild);
        }
        const widgetNow = createWidget();
        const imageDescription = widgetNow.firstElementChild.firstElementChild;
        const temperatureInfo = widgetNow.children[1].firstElementChild.firstElementChild;
        const pSummaryInfo = widgetNow.children[1].firstElementChild.children[1].firstElementChild;
        const pCityName = widgetNow.children[1].firstElementChild.children[1].children[1];
        const date = widgetNow.children[1].children[1].firstElementChild;
        let cityName;
        let result;
        if(node.nodeName == "INPUT") cityName = node.value;
        else cityName = node.textContent;
        try{
            if (node.dataset.lon != "null" && node.dataset.lat != "null"){
                // maybe api expired
                const response = await fetch(`http://api.weatherapi.com/v1/current.json?key=4b26c4d8e83642b588e105638241202&q=${node.dataset.lat},${node.dataset.lon}&aqi=no`);
                if(response.ok){
                    result = await response.json();
                }
                else{
                    alert("Nu a mers fetch-ul pentru coordonate");
                }
            }
            else {
                // api expires on 26 Feb
                const response = await fetch(`http://api.weatherapi.com/v1/current.json?key=4b26c4d8e83642b588e105638241202&q=${cityName}&aqi=no`);
                if(response.ok){
                    result = await response.json();
                }
                else{
                    alert("Nu ati introdus un oras valid");
                }
            }
        }
        catch(error){
            alert(error);
        }
        
        imageDescription.src = result.current.condition.icon;
        temperatureInfo.textContent = result.current.temp_c + "°";
        pSummaryInfo.textContent = result.current.condition.text;
        pCityName.textContent = result.location.name;
        let currentDate = new Date();
        date.textContent = currentDate.getDate() + " " + months[currentDate.getMonth()];

        weatherWidgetsContainer.append(widgetNow);

        if(weatherBoxIntroducedInRoot === false){
            root.append(divWeatherBox);
            weatherBoxIntroducedInRoot = true;
        }
        
        spinnerWeather.hidden = true;
    }
    
    
    function clearSuggestions() {
        suggestionsContainer.innerHTML = ""
    }

    async function changeBackground(name){
        const apiKey = 'OfMzGZDcfQ5L3yS2dNu3OkamY9FnEiyPbTp8WTSKcJgX8llNJY0EyraX';
        spinnerSearchBox.hidden = false;
        try{
            const response = await fetch(`https://api.pexels.com/v1/search?query=${name}%20skyline&per_page=1`, {
            method: 'GET',
            headers: {
            'Authorization': apiKey
            }
            });
            if(response.ok){
                const result = await response.json();
                if(result.total_results > 0) {
                    document.body.style.backgroundImage = `url('${result.photos[0].src.original}')`;
                }
                else document.body.style.backgroundImage = "";
            }
            else{
                alert("cv nu a mers");
            }
        }
        catch(error){
            alert(error);
        }
        spinnerSearchBox.hidden = true;

    }

    cityInput.addEventListener("keydown",function(event) {
        if(event.key == "Enter"){
            
            buttonCityFavoriteCurrentState = 0;
            buttonCityFavorite.src = buttonCityFavoriteState[0];

            if(favoriteCities.findIndex(city => city.name == cityInput.value && city.lon == cityInput.dataset.lon && city.lat == cityInput.dataset.lat) != -1){
                buttonCityFavoriteCurrentState = 1;
                buttonCityFavorite.src = buttonCityFavoriteState[1];
            }

            titleWeatherBox.textContent = cityInput.value;
            titleWeatherBox.dataset.lat = cityInput.dataset.lat;
            titleWeatherBox.dataset.lon = cityInput.dataset.lon;
            buttonHowLong.textContent = "For now";
            showWeatherNow(cityInput);
            changeBackground(cityInput.value);
            cityInput.blur();
        }
    });

    cityInput.addEventListener("blur", function() {
        clearTimeout(timeout);
        timeout = setTimeout(clearSuggestions,300);
    });

    function displaySuggestions(suggestions) {

        // Adauga sugestiile actuale
        for (let i = 0; i < suggestions.length; i++) {
            if(suggestions[i].city){
                const suggestionElement = document.createElement("li");
                suggestionElement.className = "citySuggestion";
                suggestionElement.textContent = suggestions[i].city + ", " + suggestions[i].country;
                if(suggestions[i].postcode) suggestionElement.textContent = suggestionElement.textContent + ", " + suggestions[i].postcode;
                suggestionElement.dataset.lon = suggestions[i].lon;
                suggestionElement.dataset.lat = suggestions[i].lat;
                suggestionElement.addEventListener("click", function() {
                    // Seteaza input-ul la sugestia selectata
                    const stopIndex = this.textContent.indexOf(",");
                    cityInput.value = this.textContent.substring(0, stopIndex);
                    cityInput.dataset.lon = this.dataset.lon;
                    cityInput.dataset.lat = this.dataset.lat;
                    
                    // Sterge sugestiile dupa selectare
                    clearSuggestions();
                });
                suggestionsContainer.appendChild(suggestionElement);
        }
        }
    }

    // Adauga eveniment pentru tastare
    cityInput.addEventListener("input", function() {

        clearTimeout(timeout);
        cityInput.dataset.lon = cityInput.dataset.lat = null;

        // Setează un nou timeout pentru a aștepta o secunda după ce utilizatorul oprește scrierea
        timeout = setTimeout(async function() {
            const userInput = cityInput.value.toLowerCase();
            let suggestions = [];
            if(userInput.length >= 3){
            try{
            clearSuggestions(); // sterge sugestiile vechi
            const response = await fetch(`https://api.geoapify.com/v1/geocode/autocomplete?text=${userInput}&apiKey=435054d5136548bfb6b8d416f8b3e73b`);
            const result = await response.json();
            result.features.forEach((feature,index) => {
                suggestions[index] = {};
                suggestions[index].city = feature.properties.city;
                suggestions[index].country = feature.properties.country;
                if(feature.properties.postcode) suggestions[index].postcode = feature.properties.postcode;
                suggestions[index].lat = feature.properties.lat;
                suggestions[index].lon = feature.properties.lon;
            });
            displaySuggestions(suggestions);
        }
        catch(error){
            alert(error);
        }
        }}, 1000);
    });
    
});
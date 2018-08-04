-----------------------------------------------------------------------------------------
--
-- main.lua
--
-----------------------------------------------------------------------------------------

local composer = require("composer");
local pageLoader = require("src.pageLoader");
-- Load save data would go here

local options = {
    effect = "fade",
    time = 800,
}
--pageLoader.loadPage("pageOne.txt");

composer.gotoScene( "mainMenu", options )
--composer.gotoScene("mainMenu"); -- Load options would go here
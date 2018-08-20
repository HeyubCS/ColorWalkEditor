-- This is a page object. The page object allows for easy creating of page scenes.
local page = {tag="page", pageNumber="0"}

function page:new (o)
	o = o or {};
	setmetatable(o, self);
	self.__index = self;
	return o;
end
--touchables - the objects which can be touched
--background - the image to be drawn on the lowest layer
--foreground - the image to be drawn on the front-most layer.
--number - the page number, this is used to identify the page.
--finalAnswer - when the correct touchable is touched it will reveal the next page button.
--If any extra task is needed it can be manually created using additionalFunction
--[[
	This function will create a new scene. It will add touchable objects, background, foreground, and
it will recognize when the task has been completed allowing for the next page to be played.
]]
function page:createScene(pageName)
	local composer = require( "composer" )
	local touchable = require("src.Touchable")
	 
	local scene = composer.newScene()
	 
	-- -----------------------------------------------------------------------------------
	-- Code outside of the scene event functions below will only be executed ONCE unless
	-- the scene is removed entirely (not recycled) via "composer.removeScene()"
	-- -----------------------------------------------------------------------------------
	 
	local nextLevelName = "mainMenu"
	
	 local function nextLevel(event)
	    if(event.phase == "ended") then 
	        local options = {
	                            effect = "slideLeft",
	                            time = 800,
	                            params = {levelName = nextLevelName}
	                        }
	        print(nextLevelName)
	        if(nextLevelName ~= "mainMenu") then
	            composer.gotoScene("src.page", options)
	        else
	            print("Here")
	            composer.gotoScene("mainMenu", options)
	        end
	    end
	 end
	 
	-- -----------------------------------------------------------------------------------
	-- Scene event functions
	-- -----------------------------------------------------------------------------------
	
	
	-- create()
	function scene:create( event )
	 
	    local sceneGroup = self.view
	    -- Code here runs when the scene is first created but has not yet appeared on screen
	 
	end
	 
	 
	-- show()
	function scene:show( event )
	 
	  
	    local sceneGroup = self.view
	    local phase = event.phase
	    local backgroundImage;
	    local foregroundImage;
	    local objects  = {};
	    local level = "pages." .. pageName;
	
	    if ( phase == "will" ) then
	        -- Code here runs when the scene is still off screen (but is about to come on screen)
	    local page = require(level);
	    if(page.nextLevel ~= "" and page.nextLevel ~= "mainMenu") then
	        nextLevelName = page.nextLevel;
	    else
	        nextLevelName = "mainMenu"
	    end
	    print(page.background)
	    if page.background ~= "none" and page.background ~= "null" then
	        backgroundImage = display.newImageRect(page.background, display.actualContentWidth, display.actualContentHeight);
	        backgroundImage.x = display.contentCenterX
	        backgroundImage.y = display.contentCenterY
	        sceneGroup:insert(backgroundImage)
	    end
	    if(page.foreground ~= "none" and page.foreground ~= "null") then
	        foregroundImage = display.newImageRect(page.foreground, display.actualContentwidth, display.actualContentHeight);
	        foregroundImage.x = display.contentCenterX
	        foregroundImage.y = display.contentCenterY
	        sceneGroup:insert(foregroundImage)
	    end
	    local temp = page.objects[1];
	    print(temp.ID);
	    print(temp.soundPath)
	    --touchable:createTouchable(temp);
	
	    --objects[1] = touchable:createTouchable(page.objects[1]);
	    local temp = {};
	    for i = 1, page.objectCount do --pseudocode
	        objects[i] = touchable:createTouchable(page.objects[i]);
	
	        if page.objects[i].isFinal == true then
	            objects[i]:addEventListener("touch", nextLevel)
	        end
	        sceneGroup:insert(objects[i])
	    end
	    elseif ( phase == "did" ) then
	        -- Code here runs when the scene is entirely on screen
	 
	    end
	
	end
	 
	 
	-- hide()
	function scene:hide( event )
	 
	    local sceneGroup = self.view
	    local phase = event.phase
	 
	    if ( phase == "will" ) then
	        -- Code here runs when the scene is on screen (but is about to go off screen)
	 
	    elseif ( phase == "did" ) then
	        -- Code here runs immediately after the scene goes entirely off screen
	 
	    end
	end
	 
	 
	-- destroy()
	function scene:destroy( event )
	 
	    local sceneGroup = self.view
	    -- Code here runs prior to the removal of scene's view
	 
	end
	 
	 
	-- -----------------------------------------------------------------------------------
	-- Scene event function listeners
	-- -----------------------------------------------------------------------------------
	scene:addEventListener( "create", scene )
	scene:addEventListener( "show", scene )
	scene:addEventListener( "hide", scene )
	scene:addEventListener( "destroy", scene )
	-- -----------------------------------------------------------------------------------
	 
	return scene
end
return Touchable;
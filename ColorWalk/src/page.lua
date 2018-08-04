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
function page:createScene(touchables, background, foreground, number, finalAnswer, additionalFunction)
	local composer = require( "composer" )
 
	local scene = composer.newScene()
	self.pageNumber = number;
	 
	-- -----------------------------------------------------------------------------------
	-- Code outside of the scene event functions below will only be executed ONCE unless
	-- the scene is removed entirely (not recycled) via "composer.removeScene()"
	-- -----------------------------------------------------------------------------------
	 
	 
	 
	 
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
	 
	    if ( phase == "will" ) then
	        -- Code here runs when the scene is still off screen (but is about to come on screen)
	        --Insert background
	        if background ~= nil then
	        	sceneGroup.insert(background);
	        end

			-- Insert touchables
	        for touchable in touchables do
	        	sceneGroup.insert(touchable);
	        end
			-- insert foreground
	       	if foreground ~= nil then
	        	sceneGroup.insert(foreground);
	        end
	        --Run any extra code
	        if additionalFunction ~= nil then
	     	   additionalFunction();
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
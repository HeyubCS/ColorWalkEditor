-- Main menu scene
-- 	Created 01/02/18
-- This is the menu.
local composer = require("composer");
local widget = require("widget");

local scene = composer.newScene()
 
-- -----------------------------------------------------------------------------------
-- Code outside of the scene event functions below will only be executed ONCE unless
-- the scene is removed entirely (not recycled) via "composer.removeScene()"
-- -----------------------------------------------------------------------------------
 
 
 
 
-- -----------------------------------------------------------------------------------
-- Scene event functions
-- -----------------------------------------------------------------------------------
 
 local function quitGame(event)
   if(event.phase == "ended") then
      if system.getInfo("platformName") == "Android" then
         native.requestExit()
      else
         os.exit()
      end
   end
end

local function startGame(event)
	if(event.phase == "ended") then
        local options = {
                            effect = "slideLeft",
                            time = 800,
                            params = {levelName = "ColorWalkP1"}
                        }
        composer.gotoScene("src.pageOne", options);
	end
end

local function levelEditor(event)
    if(event.phase == "ended") then
        local options = {
                            effect = "slideLeft",
                            time = 800,
                        }
        composer.gotoScene("src.levelEditor", options);
    end
end

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
 		local buttonStart = widget.newButton(
    		{
        		left = display.contentCenterX - 100,
        		top = 0,
        		id = "buttonStart",
        		label = "Start",
       			shape = "roundedRect",
        		width = 200,
        		height = 40,
        		cornerRadius = 2,
        		fillColor = { default={1,0,0,1}, over={1,0.1,0.7,0.4} },
        		strokeColor = { default={1,0.4,0,1}, over={0.8,0.8,1,1} },
        		strokeWidth = 4,
        		onEvent = startGame
    		}
		)

        local buttonlevelEditor = widget.newButton(
            {
                left = display.contentCenterX - 100,
                top = 50,
                id = "buttonStart",
                label = "Level Editor",
                shape = "roundedRect",
                width = 200,
                height = 40,
                cornerRadius = 2,
                fillColor = { default={1,0,0,1}, over={1,0.1,0.7,0.4} },
                strokeColor = { default={1,0.4,0,1}, over={0.8,0.8,1,1} },
                strokeWidth = 4,
                onEvent = levelEditor
            }
        )

		local buttonQuit = widget.newButton(
    		{
        		left = display.contentCenterX - 100 ,
        		top = 100,
        		id = "buttonQuit",
       			label = "Quit",
       			shape = "roundedRect",
        		width = 200,
        		height = 40,
        		cornerRadius = 2,
        		fillColor = { default={1,0,0,1}, over={1,0.1,0.7,0.4} },
        		strokeColor = { default={1,0.4,0,1}, over={0.8,0.8,1,1} },
        		strokeWidth = 4,
        		onEvent = quitGame
    		}
		)

		sceneGroup:insert(buttonStart);
        sceneGroup:insert(buttonlevelEditor);
		sceneGroup:insert(buttonQuit);
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